package com.example;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test3 {
	private ArrayList<Integer> arrayList = new ArrayList<>();
	Lock lock = new ReentrantLock();    //注意这个地方

	public static void main(String[] args) {
		final Test3 test = new Test3();

		new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			}
		}.start();

		new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			}
		}.start();
	}

	public void insert(Thread thread) {
		try {
			if (lock.tryLock(2, TimeUnit.SECONDS)) {
				try {
					System.out.println(thread.getName() + "得到了锁");
					for (int i = 0; i < 5; i++) {
						arrayList.add(i);
					}
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					System.out.println(thread.getName() + "释放了锁");
					lock.unlock();
				}
			} else {
				System.out.println(thread.getName() + "获取锁失败");
			}
		} catch (InterruptedException e) {
			System.out.println(thread.getName() + "获取锁异常");
			e.printStackTrace();
		}
	}

}