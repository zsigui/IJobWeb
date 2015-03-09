package com.ijob.server.utils.log;


/**
 * 
 * @description LogManager -> log管理类
 * 
 * @author Jackie Zhuang
 * @date 2014年12月12日
 */
public class LM extends AbsLog {

	private static class SingletonHolder {
		static final LM mInstance = new LM();
	}

	/**
	 * 获取LM的单一实例
	 * 
	 * @return
	 */
	public static LM ins() {
		return SingletonHolder.mInstance;
	}

	/*public static void main(String[] args) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1000;
				LM.ins().td(AbsLog.class, "测试线程1 start" + i);
				while (i-- > 0) {
					LM.ins().td(AbsLog.class, "测试线程1=" + i);
					LM.ins().te(AbsLog.class, "测试线程1=" + i);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						LM.ins().te(AbsLog.class, e.getMessage());
					}
				}
				LM.ins().td(AbsLog.class, "测试线程1结束", PrintLevel.CONSOLE.val());
			}
		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1000;
				LM.ins().td(AbsLog.class, "测试线程2 start" + i);
				while (i-- > 0)
					LM.ins().td(AbsLog.class, "测试线程2=" + i);
				LM.ins().td(AbsLog.class, "测试线程2结束=" + i,
						PrintLevel.CONSOLE.val());
			}
		}).start();
		LM.ins().td(AbsLog.class, FileUtils.ENTER, PrintLevel.CONSOLE.val());
	}*/
}
