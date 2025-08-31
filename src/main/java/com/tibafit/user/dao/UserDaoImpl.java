package com.tibafit.user.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.tibafit.user.model.User;
import com.tibafit.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public void addUser(User user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();

		try (Session session = factory.openSession()) {

			session.beginTransaction();

			// 使用新版方法將物件存入資料庫
			session.persist(user);

			// 提交
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try (Session session = factory.openSession()) {

			session.beginTransaction();

			session.merge(user);

			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAllUsers() {
		Transaction transaction = null;
		List<User> userList = null;

		// Session要用完即關
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// transaction 紀錄動作用
			transaction = session.beginTransaction();

			// 使用 HQL (Hibernate Query Language) 查詢
			// FROM User 轉成 "SELECT * FROM users
			userList = session.createQuery("FROM User", User.class).getResultList();

			// 存檔
			transaction.commit();
		} catch (Exception e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace(); // 在主控台印出錯誤訊息
		}

		return userList; // 查詢完回傳容器
	}

	@Override
	public User getUserById(int id) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try (Session session = factory.openSession()) {
			User user = session.get(User.class, id);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findByEmail(String email) {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try (Session session = factory.openSession()) {
			// 一樣用HQL語法查詢
			String hql = "FROM User WHERE email = :email";
			// 建立查詢物件
			org.hibernate.query.Query<User> query = session.createQuery(hql, User.class);
			// 設定查詢參數
			query.setParameter("email", email);
			// 執行查詢並單一結果
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
