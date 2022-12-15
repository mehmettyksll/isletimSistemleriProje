package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class BirOncelikliKuyrukClass 
{
	// Kuyruk tanýmlamalarý yapýlýyor, 
	// birOncelikliKuyruk 1 saniye calistiktan sonra, oncelik degeri dusurulecegi icin 2 oncelikli kuyruk tanýmlamasi da yapiliyor.
	public Kuyruk birOncelikliKuyruk,ikiOncelikliKuyruk;

	// constructor
	public BirOncelikliKuyrukClass()
	{
		
	}
	
	// Bu class'ýn yapacagý islem(metod)
	public  void birOncelikFonksiyon() throws IOException, InterruptedException 
	{
		birOncelikliKuyruk=Dispatcher.birOncelikliKuyruk;
		ikiOncelikliKuyruk=Dispatcher.ikiOncelikliKuyruk;
		
		/* birOncelikliKuyruk islemi */
		for(int p=0; p<birOncelikliKuyruk.count(); p++)
		{
			// Eger proses olustugundan itibaren 20 saniye gectiyse kendi kendine oluyor
			//if((Dispatcher.gecenSure-birOncelikliKuyruk.indexOf(p).enSonCalistigiZaman)>=20)
			if((Dispatcher.gecenSure-birOncelikliKuyruk.indexOf(p).varisZamani)>=20)
			{
				// proses zaten olmus mu olmemis mi bakalim
				if(birOncelikliKuyruk.indexOf(p).durum==Status.killed) // zaten olmus ise bir sey yapma
				{}
				else if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) // askýya alinmissa artik bu kuyrukta degildir(ikiOncelikliKuyruga gecmistir)
				{}
				else // olmemis ise olsun
				{
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					birOncelikliKuyruk.indexOf(p).varisZamani=0;
					//birOncelikliKuyruk.indexOf(p).oncelik=0;
					birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
					birOncelikliKuyruk.indexOf(p).killProses(Dispatcher.sayac);
				}
			}
			else // Eger proses olustuktan itibaren 20 saniye olmadýysa
			{
				// calismaSuresi=0 ise proses islemini bitirmistir(olmustur)
				if(birOncelikliKuyruk.indexOf(p).calismaSuresi==0 && birOncelikliKuyruk.indexOf(p).durum==Status.killed)
				{}
				else if(birOncelikliKuyruk.indexOf(p).calismaSuresi==1)  //gelen proses 1 saniyelik calisma zamanýna sahipse calisir ve olur 
				{
					if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //eger askiya alinmis ve bir saniyesi kalmis ise bir sey yapma
					{}
					else // proses yeni gelmis ve bir saniyelik calismaSuresi varsa calistir ve oldur
					{
						//Proses calismaya basladi // (1saniye)
						birOncelikliKuyruk.indexOf(p).startProses(Dispatcher.sayac); 
						Dispatcher.gecenSure+=1;
						Dispatcher.sayac+=1;
						// Eger proses birOncelikliKuyruk ise bir saniye calistir ve oldur.
						int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
						int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
						//birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Prosesi oldur
						birOncelikliKuyruk.indexOf(p).varisZamani=0;
						//birOncelikliKuyruk.indexOf(p).oncelik=0;
						birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
						birOncelikliKuyruk.indexOf(p).terminatedProses(Dispatcher.sayac);
						
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure, Dispatcher.kacinciSatirdayiz);
						
					}
				}
				else if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //proses askiya alinmissa
				{
					//oncelik degeri arttirildi, bir islem yapma
				}
				else // proses bitmemis ise (hala yasiyorsa ve askida degilse)
				{
					//Proses calismaya basladi
					//birOncelikliKuyruk.indexOf(p).calismaSuresi-=1;
					birOncelikliKuyruk.indexOf(p).startProses(Dispatcher.sayac); 
					Dispatcher.gecenSure+=1;
					Dispatcher.sayac+=1;
					birOncelikliKuyruk.indexOf(p).calismaSuresi-=1;
					// Eger proses birOncelikliKuyrukta ise bir saniye calistirilir ,askiya alinir ve ikiOncelikliKuyruk'a eklenir.
					int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
					int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					//birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.sayac);
					
					int varisZamani=birOncelikliKuyruk.indexOf(p).varisZamani;
					int oncelik=2;
					Status durum=Status.ready;
					String renk=birOncelikliKuyruk.indexOf(p).renk;
					int kalanCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					
					Proses proses=new Proses(prosesId,Dispatcher.gecenSure,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
					proses.enSonCalistigiZaman=Dispatcher.gecenSure;
					ikiOncelikliKuyruk.insert(proses); // iki oncelikliye eklendi
					
					//Prosesi askiya al
					birOncelikliKuyruk.indexOf(p).prosesAskiyaAlindi(Dispatcher.sayac,kalanCalismaSuresi,oncelik);
					
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					
				} // else sonu (proses bitmemis(olmemis) ise)
			}
		} // for sonu
		/* birOncelikliKuyruk islemi bitti */
	}
}
