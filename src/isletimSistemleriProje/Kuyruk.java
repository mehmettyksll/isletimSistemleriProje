package isletimSistemleriProje;

public class Kuyruk
{
	 // private deðiþkenler
	  private int maxBoyut;
	  public Proses[] kuyrukDizi;
	  private int front;
	  private int rear;
	  private int elemanSayisi;
	   
	  // constructor
	  // kuyruk degisken_ismi = new kuyruk (eleman_sayisi);
	  public Kuyruk (int boyut)
	  {
	    maxBoyut = boyut;
	    front = 0;
	    rear = -1;
	    elemanSayisi = 0;
	    kuyrukDizi = new Proses[maxBoyut];
	  }
	   
	  // metodlar
	   
	  // kuyruk boþ mu?
	  public boolean bosMu() {
	    if (elemanSayisi == 0) return true;
	    else return false;
	  }
	   
	  // kuyruk dolu mu?
	  public boolean doluMu() {
	    if (elemanSayisi == maxBoyut) return true;
	    else return false;
	  }
	   
	  // kuyruða eleman ekleme
	  public void insert (Proses eklenecekEleman){
	    if (rear == maxBoyut -1) rear = -1;
	    rear++;
	    kuyrukDizi [rear] = eklenecekEleman;
	    elemanSayisi++;
	  }
	  
	  public Proses indexOf(int index)
	  {
		  if(index>elemanSayisi)
		  {
			  return null;
		  }
		  else {
			  return kuyrukDizi[index];
		  }
		
	  }
	   
	  // kuyruktan eleman çekme
	  public Proses remove(){
		  Proses cikarilacak = kuyrukDizi [front++];
	    if (front == maxBoyut) front = 0;
	    elemanSayisi--;
	    return cikarilacak;
	  }
	   
	  // kuyruðun sonundaki eleman
	  public Proses kuyrukNerde() {
	    return kuyrukDizi[front];
	  }
	   
	  // kuyruðun boyutu
	  public int count() {
	    return elemanSayisi;
	  }
	  
}
