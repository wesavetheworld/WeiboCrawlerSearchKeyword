package Test;

public class HashSET<T> {
	T[] a;
	int N;
	int M;
	public HashSET(int cap) {
		// TODO Auto-generated constructor stub
		a=(T[])new Object[cap];
		N=0;
	}
	private int hash(T t){
		return (t.hashCode()&0x7fffffff)%M;
	}
	public void put(T t){
		
	}
	public void get(){
		
	}
	public int size(){
		return N;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
