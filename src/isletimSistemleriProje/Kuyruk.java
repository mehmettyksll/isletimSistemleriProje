package isletimSistemleriProje;

import isletimSistemleriProje.Proses.Status;

public class Kuyruk
{
	 // Class'a ait Private deðiþkenler
	  private int maxBoyut;
	  public Proses[] kuyrukDizi;
	  private int front;
	  private int rear;
	  private int elemanSayisi;
	   
	  // constructor
	  public Kuyruk (int boyut)
	  {
	    maxBoyut = boyut;
	    front = 0;
	    rear = -1;
	    elemanSayisi = 0;
	    kuyrukDizi = new Proses[maxBoyut];
	  }
	   
	  // Metodlar
	  
	  // kuyrukta okunabilecek eleman varmi
	  public boolean kuyruktaOkunacakElemanVarmi()
	  {
		  int say=0;
		  for(int i=0; i<elemanSayisi; i++)
		  {
			  if(kuyrukDizi[i].calismaSuresi==0 && kuyrukDizi[i].durum==Status.killed)
				  say++;
		  }
		  
		  if(say==elemanSayisi)
			  return true;
		  else
			  return false;
	  }
	  
	  
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
	  
	  // index'i verilen prosese erisiyoruz.
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
	// kuyruk boþ mu?
		  public boolean isEmpty() {
		    if (elemanSayisi == 0) return true;
		    else return false;
		  }
	// Round Robin kismi    
	public int prosesKaydir(int index) 
		  {
			  if(isEmpty()==false)
			  {
				  for(int index1=0; index1<elemanSayisi; index1++)
				  {
					  if(elemanSayisi==1 || (index1+1)== elemanSayisi)
						{
							break;
						}
						
						int tempId=  kuyrukDizi[index1+1].prosesId;
					    int tempVaris= kuyrukDizi[index1+1].varisZamani;
						int tempOncelik=  kuyrukDizi[index1+1].oncelik;
						int tempCalismaSure=  kuyrukDizi[index1+1].calismaSuresi;
						int tempSonZaman=  kuyrukDizi[index1+1].enSonCalistigiZaman;
						Status tempSatus=  kuyrukDizi[index1+1].durum;
						String tempRenk=kuyrukDizi[index1+1].renk;
						//saga kaydirma kismi
						kuyrukDizi[index1+1].prosesId=kuyrukDizi[index1].prosesId;
						kuyrukDizi[index1+1].varisZamani=kuyrukDizi[index1].varisZamani;
						kuyrukDizi[index1+1].oncelik=kuyrukDizi[index1].oncelik;
						kuyrukDizi[index1+1].calismaSuresi=kuyrukDizi[index1].calismaSuresi;
						kuyrukDizi[index1+1].enSonCalistigiZaman=kuyrukDizi[index1].enSonCalistigiZaman;
						kuyrukDizi[index1+1].durum=kuyrukDizi[index1].durum;
						kuyrukDizi[index1+1].renk=kuyrukDizi[index1].renk;
						
						//sag taraftaki sola aktariliyor
						kuyrukDizi[index1].prosesId=tempId;
						kuyrukDizi[index1].varisZamani=tempVaris;
						kuyrukDizi[index1].oncelik=tempOncelik;
						kuyrukDizi[index1].calismaSuresi=tempCalismaSure;
						kuyrukDizi[index1].enSonCalistigiZaman=tempSonZaman;
						kuyrukDizi[index1].durum=tempSatus;
						kuyrukDizi[index1].renk=tempRenk;
				  }
				  return 1;
			  }
			  else
			  {return 0;}
		  }
}
