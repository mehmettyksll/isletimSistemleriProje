package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class RealTimeKuyrukClass 
{
	public Kuyruk realTimeKuyruk;

	public RealTimeKuyrukClass()
	{}
	
	public void sifirOncelikFonksiyon() throws IOException
	{
		realTimeKuyruk=Dispatcher.realTimeKuyruk;
		
		/* realTimeKuyruk islemi */
		for(int j=0; j<realTimeKuyruk.count(); j++)
		{
			if(realTimeKuyruk.indexOf(j).calismaSuresi==0) // calismaSuresi=0 ise proses olmemistirr(isini bitirmistir)
			{}
			else // proses bitmemis ise (hala yasiyorsa)
			{
				// Eğer proses olustugundan itibaren 20 saniye geçtiyse kendi kendine olecek
				if((Dispatcher.gecenSure-realTimeKuyruk.indexOf(j).varisZamani)>=20)
				{
					// proses zaten ölmüş mü ölmemiş mi bakalım
					if(realTimeKuyruk.indexOf(j).durum==Status.killed) // zaten ölmüşse bir şey yapma
					{}
					else // ölmemiş ise ölsün
					{
						// Biz bu öldürme işlemini degerlerini 0'layarak belirtiyoruz.
						realTimeKuyruk.indexOf(j).varisZamani=0;
						realTimeKuyruk.indexOf(j).oncelik=0;
						realTimeKuyruk.indexOf(j).calismaSuresi=0;
						realTimeKuyruk.indexOf(j).killProses();
					}
				}
				else
				{
					realTimeKuyruk.indexOf(j).startProses();
					//bu kismida gecen sureyi zaten yukarida degistirdigimiz icin artik direkt gecenSure + calismaZamani dedim
					Dispatcher.gecenSure=Dispatcher.gecenSure + realTimeKuyruk.indexOf(j).calismaSuresi;

					// Eger proses realTimeKuyrugunda ise isi bitene kadar calistirilir ve sonlanir.
					int prosesId=realTimeKuyruk.indexOf(j).prosesId;
					int prosesinCalismaSuresi=realTimeKuyruk.indexOf(j).calismaSuresi;
					
					for(int k=0; k<prosesinCalismaSuresi; k++)
					{
						realTimeKuyruk.indexOf(j).ProsesCalistir( realTimeKuyruk.indexOf(j).prosesId ,realTimeKuyruk.indexOf(j).calismaSuresi,Dispatcher.gecenSure);	
					}
					
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					realTimeKuyruk.indexOf(j).varisZamani=0;
					realTimeKuyruk.indexOf(j).oncelik=0;
					realTimeKuyruk.indexOf(j).calismaSuresi=0;
					realTimeKuyruk.indexOf(j).terminatedProses();
					
					//Tekrar Txt'den okuma islemi yapsın ve diğer satirlari okuyarak process islemine devam etsin.
					Dispatcher.oku(Dispatcher.gecenSure, Dispatcher.kacinciSatirdayiz);
				} // else sonu(20 saniye geçti mi)
			} // else sonu
		} // for sonu
		/* realTimeKuyruk islemi bitti*/
		
	}

	
	
	
}//class sonu
