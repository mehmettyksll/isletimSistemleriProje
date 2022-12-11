package isletimSistemleriProje;

import java.io.IOException;

import isletimSistemleriProje.Proses.Status;

public class Distpathcer {
	
	private Kuyruk realTimeKuyruk;
	private DosyaOkuma _dosyaOkuma;
	Distpathcer(){
		
	}

	public void RealTimeKuyruk(int gecenSure,int enSonKalinanSatir) throws NumberFormatException, IOException 
	{
		/* realTimeKuyruk islemi */
		for(int j=0; j<realTimeKuyruk.count(); j++)
		{
			if(realTimeKuyruk.indexOf(j).calismaSuresi==0) // calismaSuresi=0 ise proses olmeyecek(isini bitirmistir)
			{}
			else // proses bitmemis ise (hala yas�yorsa)
			{
				// Eger proses olustugundan itibaren 20 saniye gectiyse kendi kendine olduruyor
				if((gecenSure-realTimeKuyruk.indexOf(j).varisZamani)>=20)
				{
					// proses zaten olumu diye kontorl ediyoruz
					if(realTimeKuyruk.indexOf(j).durum==Status.killed) // zaten olmediyse bir şey yapma
					{}
					else // olmemis ise oldurucez
					{
						// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
						realTimeKuyruk.indexOf(j).varisZamani=0;
						realTimeKuyruk.indexOf(j).oncelik=0;
						realTimeKuyruk.indexOf(j).calismaSuresi=0;
						realTimeKuyruk.indexOf(j).terminatedProses();
					}
				}
				else
				{
					realTimeKuyruk.indexOf(j).startProses();
					
					gecenSure=gecenSure + realTimeKuyruk.indexOf(j).calismaSuresi;

					// Eger proses realTimeKuyrugunda ise işi bitene kadar çalışır ve sonlanır.
					int prosesId=realTimeKuyruk.indexOf(j).prosesId;
					int prosesinCalismaSuresi=realTimeKuyruk.indexOf(j).calismaSuresi;
					
					for(int k=0; k<prosesinCalismaSuresi; k++)
					{
						realTimeKuyruk.indexOf(j).ProsesCalistir( realTimeKuyruk.indexOf(j).prosesId ,realTimeKuyruk.indexOf(j).calismaSuresi,gecenSure);	
					}
					
					// Biz bu oldurme islemini degerlerini 0'layarak belirtiyoruz.
					realTimeKuyruk.indexOf(j).varisZamani=0;
					realTimeKuyruk.indexOf(j).oncelik=0;
					realTimeKuyruk.indexOf(j).calismaSuresi=0;
					realTimeKuyruk.indexOf(j).terminatedProses();
					
					
					 
					//Tekrar Txt'den okuma islemi yapsın ve diger satırları okuyarak process islemine devam etsin.


					_dosyaOkuma.Oku(gecenSure,enSonKalinanSatir);
				} // else sonu(20 saniye gecti mi)
			} // else sonu
		} // for sonu
		/* realTimeKuyruk islemi bitti*/
	}
}
