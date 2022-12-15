package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class RealTimeKuyrukClass 
{
	public Kuyruk realTimeKuyruk; // ilgili kuyruk degiskeni tanýmlamasý

	// Constructor
	public RealTimeKuyrukClass()
	{}
	
	// Bu class'tan nesne olusturuldugunda yapabilecegi fonksiyon tanýmlaniyor
	
	public void sifirOncelikFonksiyon() throws IOException, InterruptedException
	{
		realTimeKuyruk=Dispatcher.realTimeKuyruk;
		
		/* realTimeKuyruk islemi */
		for(int j=0; j<realTimeKuyruk.count(); j++) // realTimeKuyrugunda bulunan proses sayýsý kadar calisiyor.
		{
			if(realTimeKuyruk.indexOf(j).calismaSuresi==0) // calismaSuresi=0 ise proses olmemistirr(isini bitirmistir)
			{}
			else // proses bitmemis ise (hala yasiyorsa)
			{
				// Eger proses olustugundan itibaren 20 saniye gectiyse kendi kendine olecek
				if((Dispatcher.gecenSure-realTimeKuyruk.indexOf(j).varisZamani)>=20)
				{
					// proses zaten olmus mu, olmemis mi kontrolu yapiliyor
					if(realTimeKuyruk.indexOf(j).durum==Status.killed) // Proses zaten olmus ise bir sey yapma
					{}
					else // Proses olmemis ise oldur.
					{
						// Biz bu proses oldurme islemini degerlerini 0'layarak belirtiyoruz ve killProses metodunu cagirarak yapiyoruz.
						realTimeKuyruk.indexOf(j).varisZamani=0;
						realTimeKuyruk.indexOf(j).oncelik=0;
						realTimeKuyruk.indexOf(j).calismaSuresi=0;
						realTimeKuyruk.indexOf(j).killProses(Dispatcher.gecenSure);
					}
				}
				else
				{
					realTimeKuyruk.indexOf(j).startProses(Dispatcher.sayac);
					//bu kismida gecen sureyi zaten yukarida degistirdigimiz icin artik direkt gecenSure + calismaZamani dedim
					Dispatcher.gecenSure=Dispatcher.gecenSure + realTimeKuyruk.indexOf(j).calismaSuresi;
					Dispatcher.sayac+=1;
					// Eger proses realTimeKuyrugunda ise isi bitene kadar calistirilir ve sonlanir.
					int prosesId=realTimeKuyruk.indexOf(j).prosesId;
					int prosesinCalismaSuresi=realTimeKuyruk.indexOf(j).calismaSuresi;
					
					for(int k=0; k<prosesinCalismaSuresi; k++)
					{
						if(realTimeKuyruk.indexOf(j).calismaSuresi-1==0) {
							break;
						}
						
						realTimeKuyruk.indexOf(j).ProsesCalistir( realTimeKuyruk.indexOf(j).prosesId ,realTimeKuyruk.indexOf(j).calismaSuresi,Dispatcher.sayac);	
						Dispatcher.sayac+=1;
					}
					
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					realTimeKuyruk.indexOf(j).varisZamani=0;
					realTimeKuyruk.indexOf(j).oncelik=0;
					realTimeKuyruk.indexOf(j).calismaSuresi=0;
					realTimeKuyruk.indexOf(j).terminatedProses(Dispatcher.sayac);
					
					//Tekrar Txt'den okuma islemi yapsin ve diger satirlari okuyarak process islemine devam etsin.
					Dispatcher.oku(Dispatcher.gecenSure, Dispatcher.kacinciSatirdayiz);
				} // else sonu(20 saniye geÃ§ti mi)
			} // else sonu
		} // for sonu
		/* realTimeKuyruk islemi bitti*/
		
	}
	
}//class sonu
