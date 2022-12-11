package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class IkiOncelikliKuyrukClass 
{
	public Kuyruk ikiOncelikliKuyruk,ucOncelikliKuyruk;
	
	public IkiOncelikliKuyrukClass()
	{
		
	}
	
	public void ikiOncelikFonksiyon() throws IOException 
	{
		ikiOncelikliKuyruk=Dispatcher.ikiOncelikliKuyruk;
		ucOncelikliKuyruk=Dispatcher.ucOncelikliKuyruk;
		
		/* ikiOncelikliKuyruk işlemi */
		for(int r=0; r<ikiOncelikliKuyruk.count(); r++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((Dispatcher.gecenSure-ikiOncelikliKuyruk.indexOf(r).enSonCalistigiZaman)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
					ikiOncelikliKuyruk.indexOf(r).oncelik=0;
					ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
					ikiOncelikliKuyruk.indexOf(r).killProses();
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
			}
			else // Eğer proses oluşalı 20 saniye olmadıysa
			{
				if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==0 && ikiOncelikliKuyruk.indexOf(r).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
				{}
				else if(ikiOncelikliKuyruk.indexOf(r).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
				{
					if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //eger askiya alinmiş ve bir saniyesi kalmış ise bir şey yapma
					{}
					else // proses yeni gelmiş ve bir saniyelik çalışma süresi varsa çalış ve öldür
					{
						//Proses calismaya basladi // (1saniye)
						ikiOncelikliKuyruk.indexOf(r).startProses(); 
						Dispatcher.gecenSure+=1;
						
						// Eger proses ikiOncelikliKuyruk ise bir saniye çalışır ve ölür.
						int prosesId=ikiOncelikliKuyruk.indexOf(r).prosesId;
						int prosesinCalismaSuresi=ikiOncelikliKuyruk.indexOf(r).calismaSuresi;
						ikiOncelikliKuyruk.indexOf(r).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Proses ölür
						ikiOncelikliKuyruk.indexOf(r).varisZamani=0;
						ikiOncelikliKuyruk.indexOf(r).oncelik=0;
						ikiOncelikliKuyruk.indexOf(r).calismaSuresi=0;
						ikiOncelikliKuyruk.indexOf(r).terminatedProses();
						
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					}
				}
				else if(ikiOncelikliKuyruk.indexOf(r).durum==Status.waiting) //proses askıya alınmışsa
				{
					//oncelik degeri arttırılmıştır, bir işlem yapma
					//Tekrar Txt'den okuma islemi yap
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
				else // proses bitmemiş ise (hala yaşıyorsa ve askıda degilse)
				{
					//Proses calismaya basladi
					ikiOncelikliKuyruk.indexOf(r).startProses(); 
					Dispatcher.gecenSure+=1;
					
					// Eger proses ikiOncelikliKuyruk ise bir saniye çalışır,askıya alınır ve ucOncelikliKuyruk'a eklenir.
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
					
					//Prosesi askıya al
					ikiOncelikliKuyruk.indexOf(r).prosesAskiyaAlindi();
					
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					//oku(gecenSure+1,kacinciSatirdaKaldik);
					
				} // else sonu (proses bitmemiş ise)
			}
		} // for sonu
		/* ikiOncelikliKuyruk işlemi bitti */
	}
}
