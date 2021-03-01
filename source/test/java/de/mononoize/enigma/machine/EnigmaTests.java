package de.mononoize.enigma.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import de.mononoize.enigma.machine.components.Reflector;
import de.mononoize.enigma.machine.components.Rotor;

/**
 * Tests the {@code Enigma}.
 * 
 * @author mononoize
 */
@TestMethodOrder(OrderAnnotation.class)
public class EnigmaTests {

	@Test
	@Order(101)
	public void testBuilder() {
		final Enigma.Builder builder = new Enigma.Builder();
		
		assertThrows(NullPointerException.class, () -> builder.build());
		 
		for (final Reflector reflector : Reflector.getReflectors()) {
			builder.setReflector(reflector);
			assertThrows(NullPointerException.class, () -> builder.build());
		}		
		
		for (final Rotor rotor : Rotor.getRotors()) {
			builder.setRotor1(rotor, 'A', 'A');
			assertThrows(NullPointerException.class, () -> builder.build());
		}
		
		for (final Rotor rotor : Rotor.getRotors()) {
			builder.setRotor2(rotor, 'A', 'A');
			assertThrows(NullPointerException.class, () -> builder.build());
		}
		
		for (final Rotor rotor : Rotor.getRotors()) {
			builder.setRotor3(rotor, 'A', 'A');
			assertTrue(builder.build() instanceof Enigma);
		}
		
		for (final Rotor rotor : Rotor.getRotors()) {
			builder.setRotor4(rotor, 'A', 'A');
			assertTrue(builder.build() instanceof Enigma);
		}
		
		builder.addCable('A','A');
		assertTrue(builder.build() instanceof Enigma);
	}
	
	@Test
	@Order(102)
	public void testEncodingDecoding() {
		final String text = //
				"LOREM IPSUM DOLOR SITAM ETCON SETET URSAD IPSCI NGELI TRSED DIAMN ONUMY EIRMO DTEMP ORINV " //
			  + "IDUNT UTLAB OREET DOLOR EMAGN AALIQ UYAME RATSE DDIAM VOLUP TUAAT VEROE OSETA CCUSA METJU " //
			  + "STODU ODOLO RESET EAREB UMSTE TCLIT AKASD GUBER GRENN OSEAT AKIMA TASAN CTUSE STLOR EMIPS " //
			  + "UMDOL ORSIT AMETL OREMI PSUMD OLORS ITAME TCONS ETETU RSADI PSCIN GELIT RSEDD IAMNO NUMYE " //
			  + "IRMOD TEMPO RINVI DUNTU TLABO REETD OLORE MAGNA ALIQU YAMER ATSED DIAMV OLUPT UAATV EROEO " //
			  + "SETAC CUSAM ETJUS TODUO DOLOR ESETE AREBU MSTET CLITA KASDG UBERG RENNO SEATA KIMAT ASANC " //
			  + "TUSES TLORE MIPSU MDOLO RSITA MET--";
		
		final String code = //
				"PMPXN PSNLW MNFPY EMVOZ ULLZC GLICG XMLUY ZEUVQ GDXGZ GJWBL YWGXP EQGCU IBKHU CFQPY SLQYU " //
			  + "XVHHA AUWEQ ZNZVV OYPHN BHTEU GVCBS LSCOT GTXRS CKDDP UUKAI WBRMV QFNXF CFFIZ KIIAO LKZBR " //
			  + "IFCBM LBEAD HFPZL BQPMX YAUDV KQKTH XTTJX RDFBT FVNQE FBRIJ MGQTJ ZPHMK WAMQN TCRRX QPOCL " //
			  + "IIORV QXLAC ZINMG QXYGL YFVBY TDZEI MXJLG CUEOW SRQPQ WNVVL RYTCB DYSOD YLDZB PYGCA DKCBB " //
			  + "LVGQF DMOSA UPCWE KGSCD JYVKZ TJZDV MKVTJ NZKII FAUEA MQXFK WZWYK AMLNB ZJIFD BSKWM MYROI " //
			  + "UAJPG NLCRD YACNK OPCIZ PBNBY OASOH JXQNC ZWLQE LWGPK CDDRH CPPWS FRPIV UFPCD JDGQO CYUJS " //
			  + "JPYJO SRYNV AGIXK ZSFXF VGVAV IQK--";
		
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AZ BY CX DW EV FU GT HS IR JQ") // 
				.setRotor1(Rotor.getRotorII(), 5, 'P') // 
				.setRotor2(Rotor.getRotorIII(), 3, 'F') // 
				.setRotor3(Rotor.getRotorVII(), 8, 'M') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
		assertEquals(text, enigma.decode(enigma.encode(text)));
		assertEquals(text, enigma.encode(enigma.decode(text)));
	}

	@Test
	@Order(201)
	public void testHistoricalMessage01() {
		final String text = //
				"FEIND LIQEI NFANT ERIEK OLONN EBEOB AQTET XANFA NGSUE DAUSG ANGBA ERWAL DEXEN DEDRE IKMOS " //
			  + "TWAER TSNEU STADT";
		
		final String code = //
				"GCDSE AHUGW TQGRK VLFGX UCALX VYMIG MMNMF DXTGN VHVRM MEVOU YFZSL RHDRR XFJWC FHUHM UNZEF " //
			  + "RDISI KBGPM YVXUZ";
		
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AM FI NV PS TU WZ") // 
				.setRotor1(Rotor.getRotorIII(), 22, 'L') // 
				.setRotor2(Rotor.getRotorI(), 13, 'B') //
				.setRotor3(Rotor.getRotorII(), 24, 'A') //
				.setReflector(Reflector.getReflectorA()) //
				.build();	
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
	}
	
	@Test
	@Order(202)
	public void testHistoricalMessage02() {
		final String text = 
				"AUFKL XABTE ILUNG XVONX KURTI NOWAX KURTI NOWAX NORDW ESTLX SEBEZ XSEBE ZXUAF FLIEG ERSTR " //
			  + "ASZER IQTUN GXDUB ROWKI XDUBR OWKIX OPOTS CHKAX OPOTS CHKAX UMXEI NSAQT DREIN ULLXU HRANG " //
			  + "ETRET ENXAN GRIFF XINFX RGTX-";
		
		final String code = // 
				"EDPUD NRGYS ZRCXN UYTPO MRMBO FKTBZ REZKM LXLVE FGUEY SIOZV EQMIK UBPMM YLKLT TDEIS MDICA " //
			  + "GYKUA CTCDO MOHWX MUUIA UBSTS LRNBZ SZWNR FXWFY SSXJZ VIJHI DISHP RKLKA YUPAD TXQSP INQMA " //
			  + "TLPIF SVKDA SCTAC DPBOP VHJK-";

		final Enigma enigma = new Enigma.Builder() //
				.addCables("AV BS CG DL FU HZ IN KM OW RX") //) 
				.setRotor1(Rotor.getRotorV(), 12, 'A') // 
				.setRotor2(Rotor.getRotorIV(), 21, 'L') // 
				.setRotor3(Rotor.getRotorII(), 2, 'B') // 
				.setReflector(Reflector.getReflectorB()) //
				.build();	
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
	}
	
	@Test
	@Order(203)
	public void testHistoricalMessage03() {
		final String text = 
				"SFBWD NJUSE GQOBH KRTAR EEZMW KPPRB XOHDR OEQGB BGTQV PGVKB VVGBI MHUSZ YDAJQ IROAX SSSNR " //
			  + "EHYGG RPISE ZBOVM QIEMM ZCYSG QDGRE RVBIL EKXYQ IRGIR QNRDN VRXCY YTNJR";
		
		final String code = // 
				"DREIG EHTLA NGSAM ABERS IQERV ORWAE RTSXE INSSI EBENN ULLSE QSXUH RXROE MXEIN SXINF RGTXD " //
			  + "REIXA UFFLI EGERS TRASZ EMITA NFANG XEINS SEQSX KMXKM XOSTW XKAME NECXK";

		final Enigma enigma = new Enigma.Builder() //
				.addCables("AV BS CG DL FU HZ IN KM OW RX") // 
				.setRotor1(Rotor.getRotorV(), 12, 'D') //
				.setRotor2(Rotor.getRotorIV(), 21, 'S') //
				.setRotor3(Rotor.getRotorII(), 2, 'L') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
				
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
	}
	
	@Test
	@Order(204)
	public void testHistoricalMessage04() {
		final String text = //
				"YKAEN ZAPMS CHZBF OCUVM RMDPY COFHA DZIZM EFXTH FLOLP ZLFGG BOTGO XGRET DWTJI QHLMX VJWKZ " //
			  + "UASTR";
		
		final String code = //
				"STEUE REJTA NAFJO RDJAN STAND ORTQU AAACC CVIER NEUNN EUNZW OFAHR TZWON ULSMX XSCHA RNHOR " //
			  + "STHCO";
		
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AN EZ HK IJ LR MQ OT PV SW UX") // 
				.setRotor1(Rotor.getRotorVIII(), 13, 'V') // 
				.setRotor2(Rotor.getRotorVI(), 8, 'Z') // 
				.setRotor3(Rotor.getRotorIII(), 1, 'U') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
	}
	
	@Test
	@Order(205)
	public void testHistoricalMessage05() {
		final String text = //
				"VONVO NJLOO KSJHF FTTTE INSEI NSDRE IZWOY YQNNS NEUNI NHALT XXBEI ANGRI FFUNT ERWAS SERGE " //
			  + "DRUEC KTYWA BOSXL ETZTE RGEGN ERSTA NDNUL ACHTD REINU LUHRM ARQUA NTONJ OTANE UNACH TSEYH " //
			  + "SDREI YZWOZ WONUL GRADY ACHTS MYSTO SSENA CHXEK NSVIE RMBFA ELLTY NNNNN NOOOV IERYS ICHTE " //
			  + "INSNU LL---"; 
		
		final String code = //
				"NCZWV USXPN YMINH ZXMQX SFWXW LKJAH SHNMC OCCAK UQPMK CSMHK SEINJ USBLK IOSXC KUBHM LLXCS " //
			  + "JUSRR DVKOH ULXWC CBGVL IYXEO AHXRH KKFVD REWEZ LXOBA FGYUJ QUKGR TVUKA MEURB VEKSU HHVOY " //
		      + "HABCJ WMAKL FKLMY FVNRI ZRVVR TKOFD ANJMO LBGFF LEOPR GTFLV RHOWO PBEKV WMUQF MPWPA RMFHA " //
			  + "GKXII BG---";
		
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AT BL DF GJ HM NW OP QY RZ VX") // 
				.setRotor1(Rotor.getRotorI(), 22, 'A') //
				.setRotor2(Rotor.getRotorIV(), 1, 'N') //
				.setRotor3(Rotor.getRotorII(), 1, 'J') //
				.setRotor4(Rotor.getRotorBeta(), 1, 'V') //
				.setReflector(Reflector.getReflectorBruno()) //
				.build();
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));
	}
	
	@Test
	@Order(206)
	public void testHistoricalMessage06() {
		final String text = //
				"GRUPE VONBR EMENW ELESI EBENE INSME TERAB EINSN ULNUL NULUH RUNBR AUCHB ARACH TENAB EINSV " //
			  + "IERUH RAUFA BSTIM UNGDR EISEC HSMET ERG--";
	
		final String code = 
				"ODXOD HQKXT REYPF LGSKH WOPDI JQCEL MPFEE JTKTI ASWQE HDVTO MJCOI HRWQF CKERT WNQMX GBDWW " //
			   +"FMGVJ NWPQT XXOWE OPIEY DPGDV QDNRD MJM--";
				
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AB EG JS LQ OT VY") // 
				.setRotor1(Rotor.getRotorII(), 'G', 'P') //
				.setRotor2(Rotor.getRotorVI(), 'T', 'X') //
				.setRotor3(Rotor.getRotorI(), 'A', 'Q') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));		
	}
	
	@Test
	@Order(207)
	public void testHistoricalMessage07() {
		final String text = //
				"FXDXU UUOST YFUNC QUUUF XWTTX VVVUU UEINS EINSN ULDRE IKKEI SELEK KXXIS TSECH SSTUE NDLIC " //
			  + "HESDO CKENV ORMIT TAGSA MDREI XFUNF XINRE NDSBU RGGEM XFXDX UUUOS TMOEG LICHL";
		
		final String code = 
				"TWNHY AZGBI LSHEW PGLBP QLWQE KITIA FGZHW IMCWD FXPAF EILQZ WFNRF TTQHU OADVL RLGAO QKVLW " //
			  + "LSJHW OFJJS LUVEY NRRAJ AQDKQ BGMFY CEVKP FJPKO WHHQZ YZEQR TQIKK XIXTF POEMI";
				
		final Enigma enigma = new Enigma.Builder() //
				.addCables("AE BF CM DQ HU JN LX PR SZ VW") // 
				.setRotor1(Rotor.getRotorVIII(), 'L', 'Q') //
				.setRotor2(Rotor.getRotorVI(), 'E', 'Z') //
				.setRotor3(Rotor.getRotorV(), 'P', 'V') //
				.setRotor4(Rotor.getRotorBeta(), 'E', 'M') //
				.setReflector(Reflector.getReflectorCaesar()) //
				.build();
		
		assertEquals(code, enigma.encode(text));
		assertEquals(text, enigma.decode(code));		
	}
		
}
