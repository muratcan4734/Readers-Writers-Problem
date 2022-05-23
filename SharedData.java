class SharedData {
	private ReadersWriters wr;
	private int data[] = new int[100000];

	SharedData() {
		for (int j = 0; j < data.length; j++){
			data[j] = (int) (Math.random() * 1000);}
		wr = new ReadersWriters(100000);
	}

	public int read(int k) {
		wr.startRead(k);
//		System.out.println("reading element index   " + k + "   value =   " + data[k]);
		return data[k];

	}

	public void stopRead(int k) {
		wr.stopRead(k);

	}

	public void write(int k, int x) {
		wr.startWrite(k);
//		System.out.println("Writing element index   " + k + "   value =   " + x);
		data[k] = x;
	}

	public void stopWrite(int k) {
		wr.stopWrite(k);

	}
}
