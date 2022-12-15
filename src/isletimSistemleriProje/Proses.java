package isletimSistemleriProje;

import java.io.IOException;

/*
 * 			  		  SAKARYA UNIVERSITESI 
 * 				BILGISAYAR MUHENDISLIGI BOLUMU 
 * 		  	 	 	ISLETIM SISTEMLERI DERSI
 *  				 	 PROJE ODEVI
 * 					 	 DISTPATCHER
 * 
 * 
 * 			OGRENCILER: MEHMET YUKSEL    B211210305
 * 			  			RESUL  CALISKAN  B191210002
 * 
 * 
 * */

public class Proses 
{
	// Renkler icin ilgili tanýmlama
	private static final String RESET = "\u001B[0m";

	// Proseslerin durumlari icin ilgili tanýmlama
	enum Status{
		ready,
		running,
		waiting,
		killed,
	}
	
	// Proses class'ý icin degiskenler tanýmlanýyor.
	public int prosesId;
	public int varisZamani;
	public int oncelik;
	public int calismaSuresi;
	static int enSonCalistigiZaman;
	public String renk;
	public int gecenSure;
	Status durum;
	
	// Java Process classindan process olusturuluyor olusturuluyor
	Process process;
	
	// Cmd den echo komutu ile process boþta calisiyor 
	ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "echo ");
	
	// Proses class'ýna ait Constructor
	public Proses(int _prosesId,int _gecenSure ,int _varisZamani, int _oncelik, int _calismaSuresi,Status _durum,String _renk) throws IOException
	{
		//gelen her process baslatilir
		process=pb.start();	
		
		prosesId=_prosesId;
		varisZamani=_varisZamani;
		gecenSure=_gecenSure;
		//enSonCalistigiZaman=varisZamani;
		oncelik=_oncelik;
		calismaSuresi=_calismaSuresi;
		durum=_durum;
		renk=_renk;

	}
	
	// Metodlar
	
	// Prosesin calistigini bildiriyor ve calismaSuresi islemleri yapiliyor.
	public void ProsesCalistir(int _prosesId, int _calismaSuresi,int gecenSure) 
	{
		//Baslama suresi azaltiliyor
		calismaSuresi=_calismaSuresi-1;
		enSonCalistigiZaman=gecenSure;
		
		//---------------Ekrana azaltilma bilgisi yazdiriliyor------------
		// gorsel ayarlamalar yapiliyor- ekrana yazdirma -
		if(prosesId>=0 && prosesId<10)
			System.out.print(renk+gecenSure+".0000 sn Proses yurutuluyor\t"+"\t(id:000"+_prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");		
		else if(prosesId>=10 && prosesId<100)
			System.out.print(renk+gecenSure+".0000 sn Proses yurutuluyor\t"+"\t(id:00"+_prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=100 && prosesId<1000)
			System.out.print(renk+gecenSure+".0000 sn Proses yurutuluyor\t"+"\t(id:0"+_prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=1000)
			System.out.print(renk+gecenSure+".0000 sn Proses yurutuluyor\t"+"\t(id:"+_prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// Prosesin basladigina dair metod
	public void startProses(int _gecenSure) throws IOException
	{
		// process'in baslatiliyor
		process=pb.start();
		
		gecenSure=_gecenSure;
		durum=Status.running;
		
		// gorsel ayarlamalar yapiliyor- ekrana yazdirma -
		if(prosesId>=0 && prosesId<10)
			System.out.print(renk+_gecenSure+".0000 sn Proses Basladi\t"+"\t(id:000"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		if(prosesId>=10 && prosesId<100)
			System.out.print(renk+_gecenSure+".0000 sn Proses Basladi\t"+"\t(id:00"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		if(prosesId>=100 && prosesId<1000)
			System.out.print(renk+_gecenSure+".0000 sn Proses Basladi\t"+"\t(id:0"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=1000)
			System.out.print(renk+_gecenSure+".0000 sn Proses Basladi\t"+"\t(id:"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
			
		//calismaSuresi--;
		
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Prosesin oldugune(sonlandigina) dair metod
	public void terminatedProses(int _gecenSure)
	{
		gecenSure=_gecenSure;
		durum=Status.killed;
		
		// gorsel ayarlamalar yapiliyor- ekrana yazdirma -
		if(prosesId>=0 && prosesId<10)
			System.out.print(renk+gecenSure+".0000 sn Proses Sonlandi\t"+"\t(id:000"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		if(prosesId>=10 && prosesId<100)
			System.out.print(renk+gecenSure+".0000 sn Proses Sonlandi\t"+"\t(id:00"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		if(prosesId>=100 && prosesId<1000)
			System.out.print(renk+gecenSure+".0000 sn Proses Sonlandi\t"+"\t(id:0"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=1000)
			System.out.print(renk+gecenSure+".0000 sn Proses Sonlandi\t"+"\t(id:"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
			
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// process oldurulur
		process.destroy();
	}
	
	// Prosesin oldugune dair metod
	public void killProses(int _gecenSure)
	{
		gecenSure=_gecenSure;
		durum=Status.killed;
		
		// gorsel ayarlamalar yapiliyor- ekrana yazdirma -
		if(prosesId>=0 && prosesId<10)
			System.out.print(renk+gecenSure+".0000 sn Proses Zamanasimi\t"+"\t(id:000"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=10 && prosesId<100)
			System.out.print(renk+gecenSure+".0000 sn Proses Zamanasimi\t"+"\t(id:00"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=100 && prosesId<1000)
			System.out.print(renk+gecenSure+".0000 sn Proses Zamanasimi\t"+"\t(id:0"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		else if(prosesId>=1000)
			System.out.print(renk+gecenSure+".0000 sn Proses Zamanasimi\t"+"\t(id:"+prosesId+"\toncelik:" +oncelik+"\tkalan suresi:"+calismaSuresi+" sn)"+RESET+"\n");
		
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// olusturlan proses yok zaman asimindan dolayi yok edilir
		process.destroy();
	}
	
	// Prosesin askiya alindigina dair metod
	public void prosesAskiyaAlindi(int _gecenSure, int _calismaSuresi, int _oncelik) throws InterruptedException
	{
		// askiya alma islemi
		process.waitFor();
		gecenSure=_gecenSure;
		durum=Status.waiting;
		
		// gorsel ayarlamalar yapiliyor- ekrana yazdirma -
		if(prosesId==0)
			System.out.print(renk+gecenSure+".0000 sn Proses Askida\t"+"\t\t(id:000"+prosesId+"\toncelik:" +_oncelik+"\tkalan suresi:"+(calismaSuresi)+" sn)"+RESET+"\n");
		else
		{
			if(prosesId>=0 && prosesId<10)
				System.out.print(renk+gecenSure+".0000 sn Proses Askida\t"+"\t(id:000"+prosesId+"\toncelik:" +_oncelik+"\tkalan suresi:"+(calismaSuresi)+" sn)"+RESET+"\n");
			else if(prosesId>=10 && prosesId<100)
				System.out.print(renk+gecenSure+".0000 sn Proses Askida\t"+"\t(id:00"+prosesId+"\toncelik:" +_oncelik+"\tkalan suresi:"+(calismaSuresi)+" sn)"+RESET+"\n");
			else if(prosesId>=100 && prosesId<1000)
				System.out.print(renk+gecenSure+".0000 sn Proses Askida\t"+"\t(id:0"+prosesId+"\toncelik:" +_oncelik+"\tkalan suresi:"+(calismaSuresi)+" sn)"+RESET+"\n");
			else if(prosesId>=1000)
				System.out.print(renk+gecenSure+".0000 sn Proses Askida\t"+"\t(id:"+prosesId+"\toncelik:" +_oncelik+"\tkalan suresi:"+(calismaSuresi)+" sn)"+RESET+"\n");
		}
		
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

