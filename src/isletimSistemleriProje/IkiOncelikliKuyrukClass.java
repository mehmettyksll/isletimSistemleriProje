package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class IkiOncelikliKuyrukClass 
{
	// Kuyruk tanýmlamalarý yapýlýyor, 
	// ikiOncelikliKuyruk 1 saniye calistiktan sonra, oncelik degeri dusurulecegi icin 3 oncelikli kuyruk tanýmlamasi da yapiliyor.
	public Kuyruk ikiOncelikliKuyruk,ucOncelikliKuyruk; 
	
	// constructor
	public IkiOncelikliKuyrukClass()
	{
		
	}
	
	// Bu class'ýn yapacagý islem(metod)
	public void ikiOncelikFonksiyon() throws IOException 
	{
		ikiOncelikliKuyruk=Dispatcher.ikiOncelikliKuyruk;
		ucOncelikliKuyruk=Dispatcher.ucOncelikliKuyruk;
		
		/* ikiOncelikliKuyruk islemi */
		for(int r=0; r<ikiOncelikliKuyruk.count(); r++)
		{
			// Eger proses olustugundan itibaren 20 saniye gectiyse kendi kendine oluyor
			//if((Dispatcher.gecenSure-ikiOncelikliKuyruk.indexOf(r).enSonCalistigiZaman)>=20)
			if((Dispatcher.gecenSure-ikiOncelikliKuyruk.indexOf(r).varisZamani)>=20)
			{
				// proses zaten olmus mu olmemis mi bakalim
				if(ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) // zaten olmus ise bir sey yapma
				{}
				else // olmemis ise olsun
				{
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
					ikiOncelikliKuyruk.indexOf(r).oncelik=0;
					ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
					ikiOncelikliKuyruk.indexOf(r).killProses();
				}
			}
			else // Eger proses olustuktan itibaren 20 saniye olmadýysa
			{
				// calismaSuresi=0 ise proses islemini bitirmistir(olmustur)
				if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==0 && ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) 
				{}
				else if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==1) //gelen proses 1 saniyelik calisma zmaanýna sahipse calisir ve olur 
				{
					if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //eger askiya alinmis ve bir saniyesi kalmis ise bir sey yapma
					{}
					else // proses yeni gelmis ve bir saniyelik calismaSuresi varsa calistir ve oldur
					{
						//Proses calismaya basladi // (1saniye)
						ikiOncelikliKuyruk.indexOf(r).startProses(); 
						Dispatcher.gecenSure+=1;
						
						// Eger proses ikiOncelikliKuyruk ise bir saniye calistir ve oldur.
						int prosesId=ikiOncelikliKuyruk.indexOf(r).prosesId;
						int prosesinCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
						ikiOncelikliKuyruk.indexOf(r).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Proses olsun
						ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
						ikiOncelikliKuyruk.indexOf(r).oncelik=0;
						ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
						ikiOncelikliKuyruk.indexOf(r).terminatedProses();
						
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					}
				}
				else if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //proses askiya alinmis ise
				{
					//oncelik degeri arttirildi, bir islem yapma
				}
				else // proses bitmemis ise (hala yasiyorsa ve askida degilse)
				{
					//Proses calismaya basladi
					ikiOncelikliKuyruk.indexOf(r).startProses(); 
					Dispatcher.gecenSure+=1;
					
					// Eger proses ikiOncelikliKuyruk ise bir saniye calistirilir ,askiya alinir ve ucOncelikliKuyruk'a eklenir.
					int prosesId=ikiOncelikliKuyruk.indexOf(r).prosesId;
					int prosesinCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
					ikiOncelikliKuyruk.indexOf(r).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
					int varisZamani=ikiOncelikliKuyruk.indexOf(r).varisZamani;
					int oncelik=3;
					Status durum=Status.ready;
					String renk=ikiOncelikliKuyruk.indexOf(r).renk;
					int kalanCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
					Proses proses=new Proses(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
					proses.enSonCalistigiZaman=Dispatcher.gecenSure;
					ucOncelikliKuyruk.insert(proses); // ucOncelikliKuyruk'a eklendi
					
					//Prosesi askiya alindi
					ikiOncelikliKuyruk.indexOf(r).prosesAskiyaAlindi();
					
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					
				} // else sonu (proses bitmemis(olmemis) ise)
			}
		} // for sonu
		/* ikiOncelikliKuyruk islemi bitti */
	}
}
