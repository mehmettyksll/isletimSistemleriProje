package isletimSistemleriProje;

public class Proses 
{
	// Renkler icin ilgili tan�mlama
	private static final String RESET = "\u001B[0m";

	// Proseslerin durumlari icin ilgili tan�mlama
	enum Status{
		ready,
		running,
		waiting,
		killed,
	}
	
	// Proses class'� icin degiskenler tan�mlan�yor.
	public int prosesId;
	public int varisZamani;
	public int oncelik;
	public int calismaSuresi;
	static int enSonCalistigiZaman;
	public String renk;
	Status durum;
	
	// Proses class'�na ait Constructor
	public Proses(int _prosesId, int _varisZamani, int _oncelik, int _calismaSuresi,Status _durum,String _renk)
	{
		prosesId=_prosesId;
		varisZamani=_varisZamani;
		//enSonCalistigiZaman=varisZamani;
		oncelik=_oncelik;
		calismaSuresi=_calismaSuresi;
		durum=_durum;
		renk=_renk;
	}
	
	// Metodlar
	
	// Prosesin calistigini bildiriyor ve calismaSuresi islemleri yapiliyor.
	public void ProsesCalistir(int _prosesId, int _calismaSuresi,int gecenSure) {
		
	
		//Baslama suresi azaltiliyor
		calismaSuresi=_calismaSuresi-1;
		enSonCalistigiZaman=gecenSure;
		//Ekrana çalıştırılma bilgisi yazdırılıyor
		System.out.print(renk+" "+_prosesId+" Id li " +oncelik+" oncelikli "+ "proses calisiyor..." +" Kalan sure:"+calismaSuresi+RESET+"\n");		
		
		//1 saniye islem suresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Prosesin basladigina dair metod
	public void startProses()
	{
		durum=Status.running;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Basladi!"+RESET);
	}

	// Prosesin oldugune dair metod
	public void terminatedProses()
	{
		durum=Status.killed;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Sonlandi!"+RESET);
	}
	
	// Prosesin oldugune dair metod
	public void killProses()
	{
		durum=Status.killed;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Olduruldu!!!!"+RESET);
	}
	
	// Prosesin askiya alindigina dair metod
	public void prosesAskiyaAlindi()
	{
		durum=Status.waiting;
		System.out.println(renk+prosesId+" Id kimligin sahip Proses Askiya Alindi!"+RESET);
	}
}
