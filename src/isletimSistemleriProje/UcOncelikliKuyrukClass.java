package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class UcOncelikliKuyrukClass 
{
	public Kuyruk ucOncelikliKuyruk;
	
	public UcOncelikliKuyrukClass()
	{
		
	}
	
	public void ucOncelikFonksiyon() throws IOException
	{
		ucOncelikliKuyruk=Dispatcher.ucOncelikliKuyruk;
		
		/* ucOncelikliKuyruk işlemi*/
		for(int s=0; s<ucOncelikliKuyruk.count(); s++)
		{
			// Eğer proses oluştuğundan itibaren 20 saniye geçtiyse kendi kendine ölüyor
			if((Dispatcher.gecenSure-ucOncelikliKuyruk.indexOf(s).enSonCalistigiZaman)>=20)
			{
				// proses zaten ölmüş mü ölmemiş mi bakalım
				if(ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // zaten ölmüşse bir şey yapma
				{}
				else // ölmemiş ise ölsün
				{
					// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
					ucOncelikliKuyruk.indexOf(s).varisZamani=0;
					ucOncelikliKuyruk.indexOf(s).oncelik=0;
					ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
					ucOncelikliKuyruk.indexOf(s).killProses();
					//oku(gecenSure,kacinciSatirdaKaldik);
				}
			}
			else // Eğer proses oluşalı 20 saniye olmadıysa, işlemlerini gerçekleştirsin
			{
				if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==0 && ucOncelikliKuyruk.indexOf(s).durum==Status.killed) // calismaSuresi=0 ise proses işlemini bitirmiştir ()
				{} // proses ölmüştür
				else if(ucOncelikliKuyruk.indexOf(s).calismaSuresi==1) //gelen proses 1 saniyelik calisma zamanına sahipse calisir ve ölür 
				{
						//Proses calismaya basladi // (1saniye)
						ucOncelikliKuyruk.indexOf(s).startProses(); 
						
						//System.out.println(gecenSure);
						int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
						int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
						ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
						
						// Proses ölür
						ucOncelikliKuyruk.indexOf(s).varisZamani=0;
						ucOncelikliKuyruk.indexOf(s).oncelik=0;
						ucOncelikliKuyruk.indexOf(s).calismaSuresi=0;
						ucOncelikliKuyruk.indexOf(s).terminatedProses();
						
						Dispatcher.gecenSure+=1;
						//Tekrar Txt'den okuma islemi yap
						Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
				}
				else // proses bitmemiş ise (hala yaşıyorsa/ölmediyse)
				{
					//Proses calismaya basladi
					ucOncelikliKuyruk.indexOf(s).startProses(); 
					
					// Bir saniye calistir ve askiya al
					int prosesId=ucOncelikliKuyruk.indexOf(s).prosesId;
					int prosesinCalismaSuresi=ucOncelikliKuyruk.indexOf(s).calismaSuresi;
					ucOncelikliKuyruk.indexOf(s).ProsesCalistir(prosesId, prosesinCalismaSuresi,Dispatcher.gecenSure);
					//System.out.print(ucOncelikliKuyruk.indexOf(s).enSonCalistigiZaman);
					//Prosesi askıya al
					ucOncelikliKuyruk.indexOf(s).prosesAskiyaAlindi();
					//System.out.print("index:"+s);
					Dispatcher.gecenSure+=1;
					ucOncelikliKuyruk.prosesKaydir(s);
					//Tekrar Txt'den okuma islemi yap
					Dispatcher.oku(Dispatcher.gecenSure,Dispatcher.kacinciSatirdayiz);
					//oku(gecenSure+1,kacinciSatirdaKaldik);
				}
			}
		} // for sonu
		/* ucOncelikliKuyruk işlemi bitti */
	}
}
