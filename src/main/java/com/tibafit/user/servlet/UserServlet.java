package com.tibafit.user.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.tibafit.user.dao.UserDaoImpl;
import com.tibafit.user.model.User;
import com.tibafit.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/user")
@MultipartConfig(maxFileSize = 1024 * 1024 * 2, // 限制單一檔案大小為 2MB
		maxRequestSize = 1024 * 1024 * 5 // 限制整個請求大小為 5MB
)
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDaoImpl userDao;

	public void init() {
		userDao = new UserDaoImpl();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json;charset=UTF-8");

		// 從 Session 中取得當前登入者的 Email
		HttpSession session = req.getSession(false); // false 表示如果沒有 session 就不會建立新的

		if (session == null || session.getAttribute("userEmail") == null) {
			resp.getWriter().write("{\"error\": \"使用者未登入\"}");
			return;
		}
		String email = (String) session.getAttribute("userEmail");
		try {
			// 查詢使用者
			User user = userDao.findByEmail(email);

			Gson gson = new Gson();
			String jsonOutput = gson.toJson(user);

			// 將 JSON 回傳給客戶端
			resp.getWriter().write(jsonOutput);

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("{\"error\": \"發生錯誤: " + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=UTF-8");
		req.setCharacterEncoding("UTF-8"); // 加上這行來處理中文參數

		// 從 Session 中取得當前登入者的 Email
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("userEmail") == null) {
			resp.getWriter().write("{\"success\": false, \"message\": \"使用者未登入，無法更新資料\"}");
			return;
		}
		String userEmail = (String) session.getAttribute("userEmail");
		try {

			// 1. 先從資料庫取得最新的 User 物件
			User user = userDao.findByEmail(userEmail);

			// 2. 檢查使用者是否存在
			if (user != null) {
				// --- 更新所有文字資料 ---
				String nickname = req.getParameter("nickname");
				String name = req.getParameter("name");
				String phone = req.getParameter("phone");
				String genderStr = req.getParameter("gender");
				String heightStr = req.getParameter("heightCm");
				String weightStr = req.getParameter("weightKg");

				// 姓名欄位的後端驗證
				String nameError = ValidationUtil.validateName(name);
				if (nameError != null) {
					resp.getWriter().write("{\"success\": false, \"message\": \"" + nameError + "\"}");
					return;
				}
				// 檢查並更新 user 物件的屬性
				if (nickname != null && !nickname.isEmpty()) {
					user.setNickName(nickname);
				}
				if (name != null && !name.isEmpty()) {
					user.setName(name);
				}
				if (phone != null && !phone.isEmpty()) {
					user.setPhone(phone);
				}
				if (genderStr != null && !genderStr.isEmpty()) {
					user.setGender(Integer.parseInt(genderStr));
				}
				if (heightStr != null && !heightStr.isEmpty()) {
					user.setHeightCm(new BigDecimal(heightStr));
				}
				if (weightStr != null && !weightStr.isEmpty()) {
					user.setWeightKg(new BigDecimal(weightStr));
				}

				// --- 處理檔案上傳 ---
				Part filePart = req.getPart("profilePicture");

				// 只有在使用者真的有上傳新檔案時，才處理檔案
				if (filePart != null && filePart.getSize() > 0) {
					String fileName = filePart.getSubmittedFileName();

					// (刪除舊檔案的邏輯...)
					String oldProfilePicturePath = user.getProfilePicture();
					if (oldProfilePicturePath != null && !oldProfilePicturePath.isEmpty()
							&& !oldProfilePicturePath.endsWith("default-avatar.png")) {
						String oldFilePath = getServletContext().getRealPath(oldProfilePicturePath);
						File oldFile = new File(oldFilePath);
						if (oldFile.exists()) {
							oldFile.delete();
						}
					}

					// (儲存新檔案的邏輯...)
					String savePath = getServletContext().getRealPath("/assets/images/profiles");
					File uploadDir = new File(savePath);
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}
					try (InputStream fileContent = filePart.getInputStream();
							FileOutputStream fos = new FileOutputStream(savePath + File.separator + fileName)) {
						byte[] buffer = new byte[1024];
						int bytesRead;
						while ((bytesRead = fileContent.read(buffer)) != -1) {
							fos.write(buffer, 0, bytesRead);
						}
					}

					// 設定新的圖片路徑
					user.setProfilePicture("assets/images/profiles/" + fileName);
				}

				// 3. 最後，將更新完畢的 user 物件存回資料庫
				userDao.updateUser(user);

				// 4. 回傳成功訊息
				resp.getWriter().write("{\"success\": \"更新成功！\"}");

			} else {
				// 如果找不到使用者
				resp.getWriter().write("{\"error\": \"找不到使用者。\"}");
			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("{\"error\": \"更新失敗: " + e.getMessage() + "\"}");
		}
	}
}