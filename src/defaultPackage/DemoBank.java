package defaultPackage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DemoBank {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Bank b=new Bank();
		
		for(int i=0; i<100;i++) {
			ExecuteTransfer execute=new ExecuteTransfer(b,i,2000);	
			
			Thread t=new Thread(execute);
			t.start();	
		}

	}

}

class Bank {
	private final double[] accounts;
	//private Lock myLock=new ReentrantLock();
	//private Condition enoughBalance;

	public Bank() {
		accounts = new double[100];

		for (int i = 0; i < accounts.length; i++) {
			accounts[i] = 2000;
		}
		
		// Establece una condicion para cada hilo
		//enoughBalance=myLock.newCondition();
		
	}

	public synchronized void transfer(int originAccount, int destinyAccount, double amount) throws InterruptedException {
		
		//Bloquea el método para que se pueda ejecutar solo un hilo
		//myLock.lock();
		
		//try {
			
			// Pone el hilo a la espera dado el caso la transferencia sea mayor al saldo
			while (accounts[originAccount] < amount) {
				//enoughBalance.await();
				wait();
			}

			System.out.print(Thread.currentThread() + " "); // Imprime hilo actual

			accounts[originAccount] -= amount; // Descuenta saldo

			System.out.printf(" %10.2f de %d para %d", amount, originAccount, destinyAccount);

			accounts[destinyAccount] += amount; // suma saldo en la cuenta destino

			System.out.printf(". El saldo es de %10.2f" ,getBalance());
			
			System.out.println();
			
			notifyAll();
			
			//enoughBalance.signalAll(); // Despierta los hilos que están a la espera
			
			
			
		//}//finally {
			//myLock.unlock();
		//}
		
		

	}

	public Double getBalance() {
		double counter = 0;
		for (double i : accounts) {
			counter += i;
		}

		return counter;
	}

}

class ExecuteTransfer implements Runnable {

	private Bank bank;
	private int fromTheAccount;
	private double maxAllowed;

	public ExecuteTransfer(Bank b, int from, double max) {
		bank = b;
		fromTheAccount = from;
		maxAllowed = max;
	}

	public void run() {

		try {
			while (true) {

				int toTheAccount = (int) (100 * Math.random()); // Crea la cuenta destino
				double amountToTransfer = maxAllowed * Math.random(); // Obtiene la cantidad a transferir

				bank.transfer(fromTheAccount, toTheAccount, amountToTransfer); // Crea instancia de tipo bank

				Thread.sleep((int) Math.random() * 10); // Duerme el hilo para que no se ejecute tán rápido
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} // fin clase ExecuteTransfer




