package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class UcOncelikliKuyrukClass 
{
	public Kuyruk ucOncelikliKuyruk; // Gerekli kuyruk degiskeni tanimlaniyor
	
	// Constructor
	public UcOncelikliKuyrukClass()
	{
		
	}
	
	// Bu class'tan nesne olusturuldugunda bir metod cagirilacagi icin bu metod tanimlaniyor
	public void ucOncelikFonksiyon() throws IOException
	{
		ucOncelikliKuyruk=Dispatcher.ucOncelikliKuyruk;
		
		/* ucOncelikliKuyruk islemi*/
		for(int s=0; s<ucOncelikliKuyruk.count(); s++)
		{
			// Eger proses olusturulduktan itibaren 20 saniye gectiyse kendi kendine oluyor, bunun kontrolu yapiliyor
			if((Dispatcher.gecenSure-ucOncelikliKuyruk.indexOf(s).enSonCalistigiZaman)>=20)
			{
				// proses zaten olmus mu, olmemis mi buna bakalim
				if(ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // zaten olmus ise bir sey yapma
				{}
				else // Proses olmemis ise oldur
				{
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					ucOncelikliKuyruk.indexOf(s).varisZamani=0;
					ucOncelikliKuyruk.indexOf(s).oncelik=0;
					ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
					ucOncelikliKuyruk.indexOf(s).killProses();
				}
			}
			else // Eger proses olustuktan itibaren 20 saniye olmadiysa, islemlerini gerceklestirsin
			{
				if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==0 && ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // calismaSuresi=0 ise proses islemini bitirmistir(olmustur)
				{} // proses olmustur
				else if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanina sahipse calisir ve olur
				{
						//Proses calismaya basladi // (1saniye)
						ucOncelikliKuyruk.indexOf(s).startProses(); 
						
						int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
						int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
						ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Proses olsun
						ucOncelikliKuyruk.indexOf(s).varisZamani=0;
						ucOncelikliKuyruk.indexOf(s).oncelik=0;
						ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
						ucOncelikliKuyruk.indexOf(s).terminatedProses();
						
						Dispatcher.gecenSure+=1;
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
				}
				else // proses bitmemis ise (hala yasiyorsa(olmediyse))
				{
					//Proses calismaya basladi
					ucOncelikliKuyruk.indexOf(s).startProses(); 
					
					// Bir saniye calistir ve askiya al
					int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
					int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
					ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
					
					//Prosesi askıya al
					ucOncelikliKuyruk.indexOf(s).prosesAskiyaAlindi();
					
					Dispatcher.gecenSure+=1;
					ucOncelikliKuyruk.prosesKaydir(s);
					
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
				}
			}
		} // for sonu
		/* ucOncelikliKuyruk islemi bitti */
	}
}
