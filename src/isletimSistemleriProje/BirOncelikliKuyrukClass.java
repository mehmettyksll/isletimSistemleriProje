package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class BirOncelikliKuyrukClass 
{
	public Kuyruk birOncelikliKuyruk,ikiOncelikliKuyruk;
	
	public BirOncelikliKuyrukClass()
	{
		
	}
	
	public  void birOncelikFonksiyon() throws IOException 
	{
		birOncelikliKuyruk=Dispatcher.birOncelikliKuyruk;
		ikiOncelikliKuyruk=Dispatcher.ikiOncelikliKuyruk;
		
		/* birOncelikliKuyruk işlemi */
		for(int p=0; p<birOncelikliKuyruk.count(); p++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((Dispatcher.gecenSure-birOncelikliKuyruk.indexOf(p).enSonCalistigiZaman)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(birOncelikliKuyruk.indexOf(p).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					birOncelikliKuyruk.indexOf(p).varisZamani=0;
					birOncelikliKuyruk.indexOf(p).oncelik=0;
					birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
					birOncelikliKuyruk.indexOf(p).killProses();
					//oku(gecenSure,(kacinciSatirdaKaldik+1));
				}
			}
			else
			{
				if(birOncelikliKuyruk.indexOf(p).calismaSuresi==0 && birOncelikliKuyruk.indexOf(p).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
				{}
				else if(birOncelikliKuyruk.indexOf(p).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
				{
					if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //eger askiya alinmiş ve bir saniyesi kalmış ise bir şey yapma
					{}
					else // proses yeni gelmiş ve bir saniyelik çalışma süresi varsa çalış ve öldür
					{
						//Proses calismaya basladi // (1saniye)
						birOncelikliKuyruk.indexOf(p).startProses(); 
						Dispatcher.gecenSure+=1;
						
						// Eger proses birOncelikliKuyruk ise bir saniye çalışır ve ölür.
						int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
						int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
						birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Proses ölür
						birOncelikliKuyruk.indexOf(p).varisZamani=0;
						birOncelikliKuyruk.indexOf(p).oncelik=0;
						birOncelikliKuyruk.indexOf(p).calismaSuresi=0;
						birOncelikliKuyruk.indexOf(p).terminatedProses();
						
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure, Dispatcher.kacinciSatirdayiz);
						
					}
				}
				else if(birOncelikliKuyruk.indexOf(p).durum==Status.waiting) //proses askıya alınmışsa
				{
					//oncelik degeri arttırılmıştır, bir işlem yapma
					//Tekrar Txt'den okuma islemi yap
					//oku(oVarisZamaninaKadarOku, kacinciSatirdaKaldik) => oku bunları alıyor
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
				else // proses bitmemiş ise (hala yaşıyorsa ve askıda degilse)
				{
					//Proses calismaya basladi
					birOncelikliKuyruk.indexOf(p).startProses(); 
					Dispatcher.gecenSure+=1;
					
					// Eger proses birOncelikliKuyruk ise bir saniye çalışır,askıya alınır ve ikiOncelikliKuyruk'a eklenir.
					int prosesId=birOncelikliKuyruk.indexOf(p).prosesId;
					int prosesinCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					birOncelikliKuyruk.indexOf(p).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
					
					int varisZamani=birOncelikliKuyruk.indexOf(p).varisZamani;
					int oncelik=2;
					Status durum=Status.ready;
					String renk=birOncelikliKuyruk.indexOf(p).renk;
					int kalanCalismaSuresi=birOncelikliKuyruk.indexOf(p).calismaSuresi;
					//System.out.println("kalanCalismaSuresi: "+kalanCalismaSuresi);
					Proses proses=new Proses(prosesId,varisZamani,oncelik,kalanCalismaSuresi,durum,renk);
					proses.enSonCalistigiZaman=Dispatcher.gecenSure;
					ikiOncelikliKuyruk.insert(proses); // iki öncelikliye eklendi
					
					//Prosesi askıya al
					birOncelikliKuyruk.indexOf(p).prosesAskiyaAlindi();
					
					//System.out.println(birOncelikliKuyruk.indexOf(p).calismaSuresi);
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					//oku(gecenSure+1,kacinciSatirdaKaldik);
					
				} // else sonu (proses bitmemiş ise)
			}
		} // for sonu
		/* birOncelikliKuyruk işlemi bitti */
	}
}
