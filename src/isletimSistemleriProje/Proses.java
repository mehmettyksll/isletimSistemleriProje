package isletimSistemleriProje;

public class Proses 
{
	//Renkler
	private static final String RESET = "\u001B[0m";

	enum Status{
		ready,
		running,
		waiting,
		killed,
	}
	public int prosesId;
	public int varisZamani;
	public int oncelik;
	public int calismaSuresi;
	static int enSonCalistigiZaman;
	public String renk;
	Status durum;
	
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
	public void ProsesCalistir(int _prosesId, int _calismaSuresi,int gecenSure) {
		
	
		//Baslama süresi azaltiliyor
		calismaSuresi=_calismaSuresi-1;
		enSonCalistigiZaman=gecenSure;
		//Ekrana çalıştırılma bilgisi yazdırılıyor
		System.out.print(renk+" "+_prosesId+" Id li " +oncelik+" oncelikli "+ "proses calisiyor..." +" Kalan sure:"+calismaSuresi+RESET+"\n");		
		
		//1 saniye islem süresi
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void startProses()
	{
		durum=Status.running;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Basladi!"+RESET);
	}

	public void terminatedProses()
	{
		durum=Status.killed;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Sonlandi!"+RESET);
	}
	public void killProses()
	{
		durum=Status.killed;
		System.out.println(renk+prosesId+" Id kimligine sahip Proses Olduruldu!!!!"+RESET);
	}
	
	public void prosesAskiyaAlindi()
	{
		durum=Status.waiting;
		System.out.println(renk+prosesId+" Id kimligin sahip Proses Askiya Alindi!"+RESET);
	}
}
