package defaultPackage;

public class Sincronizando {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HilosVarios hilo1 = new HilosVarios();
		HilosVarios2 hilo2 = new HilosVarios2(hilo1);

		hilo2.start();
		hilo1.start();

	}

}

class HilosVarios extends Thread {

	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("Ejecutando Hilo " + getName());

			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}

class HilosVarios2 extends Thread {

	private Thread hilo;

	public HilosVarios2(Thread hilo) {
		this.hilo = hilo;
	}

	public void run() {

		try {

			hilo.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < 5; i++) {
			System.out.println("Ejecutando Hilo " + getName());

			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
