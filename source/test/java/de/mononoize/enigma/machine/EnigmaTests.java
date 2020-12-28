package de.mononoize.enigma.machine;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		
		final Enigma encodeEnigma = new Enigma.Builder() //
				.addCable('A', 'Z') //
				.addCable('B', 'Y') //
				.addCable('C', 'X') //
				.addCable('D', 'W') //
				.addCable('E', 'V') //
				.addCable('F', 'U') //
				.addCable('G', 'T') //
				.addCable('H', 'S') //
				.addCable('I', 'R') //
				.addCable('J', 'Q') //
				.setRotor1(Rotor.getRotorII(), 5, 'P') //
				.setRotor2(Rotor.getRotorIII(), 3, 'F') //
				.setRotor3(Rotor.getRotorVII(), 8, 'M') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		final Enigma decodeEnigma = new Enigma.Builder() //
				.addCable('A', 'Z') //
				.addCable('B', 'Y') //
				.addCable('C', 'X') //
				.addCable('D', 'W') //
				.addCable('E', 'V') //
				.addCable('F', 'U') //
				.addCable('G', 'T') //
				.addCable('H', 'S') //
				.addCable('I', 'R') //
				.addCable('J', 'Q') //
				.setRotor1(Rotor.getRotorII(), 5, 'P') //
				.setRotor2(Rotor.getRotorIII(), 3, 'F') //
				.setRotor3(Rotor.getRotorVII(), 8, 'M') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		assertEquals(code, encodeEnigma.decode(text));
		assertEquals(text, decodeEnigma.decode(code));
		assertEquals(text, decodeEnigma.decode(encodeEnigma.decode(text)));
		assertEquals(text, encodeEnigma.decode(decodeEnigma.decode(text)));
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
		
		final Enigma encodeEnigma = new Enigma.Builder() //
				.addCable('A', 'M') //
				.addCable('F', 'I') //
				.addCable('N', 'V') //
				.addCable('P', 'S') //
				.addCable('T', 'U') //
				.addCable('W', 'Z') //
				.setRotor1(Rotor.getRotorIII(), 22, 'L') //
				.setRotor2(Rotor.getRotorI(), 13, 'B') //
				.setRotor3(Rotor.getRotorII(), 24, 'A') //
				.setReflector(Reflector.getReflectorA()) //
				.build();
		
		final Enigma decodeEnigma = new Enigma.Builder() //
				.addCable('A', 'M') //
				.addCable('F', 'I') //
				.addCable('N', 'V') //
				.addCable('P', 'S') //
				.addCable('T', 'U') //
				.addCable('W', 'Z') //
				.setRotor1(Rotor.getRotorIII(), 22, 'L') //
				.setRotor2(Rotor.getRotorI(), 13, 'B') //
				.setRotor3(Rotor.getRotorII(), 24, 'A') //
				.setReflector(Reflector.getReflectorA()) //
				.build();
		
		assertEquals(code, encodeEnigma.decode(text));
		assertEquals(text, decodeEnigma.decode(code));
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

		final Enigma encodeEnigma = new Enigma.Builder() //
				.addCable('A', 'V') //
				.addCable('B', 'S') //
				.addCable('C', 'G') //
				.addCable('D', 'L') //
				.addCable('F', 'U') //
				.addCable('H', 'Z') //
				.addCable('I', 'N') //
				.addCable('K', 'M') //
				.addCable('O', 'W') //
				.addCable('R', 'X') //
				.setRotor1(Rotor.getRotorV(), 12, 'A') //
				.setRotor2(Rotor.getRotorIV(), 21, 'L') //
				.setRotor3(Rotor.getRotorII(), 2, 'B') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		final Enigma decodeEnigma = new Enigma.Builder() //
				.addCable('A', 'V') //
				.addCable('B', 'S') //
				.addCable('C', 'G') //
				.addCable('D', 'L') //
				.addCable('F', 'U') //
				.addCable('H', 'Z') //
				.addCable('I', 'N') //
				.addCable('K', 'M') //
				.addCable('O', 'W') //
				.addCable('R', 'X') //
				.setRotor1(Rotor.getRotorV(), 12, 'A') //
				.setRotor2(Rotor.getRotorIV(), 21, 'L') //
				.setRotor3(Rotor.getRotorII(), 2, 'B') //
				.setReflector(Reflector.getReflectorB()) //
				.build();	
		
		assertEquals(code, encodeEnigma.decode(text));
		assertEquals(text, decodeEnigma.decode(code));
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

		final Enigma encodeEnigma = new Enigma.Builder() //
				.addCable('A', 'V') //
				.addCable('B', 'S') //
				.addCable('C', 'G') //
				.addCable('D', 'L') //
				.addCable('F', 'U') //
				.addCable('H', 'Z') //
				.addCable('I', 'N') //
				.addCable('K', 'M') //
				.addCable('O', 'W') //
				.addCable('R', 'X') //
				.setRotor1(Rotor.getRotorV(), 12, 'D') //
				.setRotor2(Rotor.getRotorIV(), 21, 'S') //
				.setRotor3(Rotor.getRotorII(), 2, 'L') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		final Enigma decodeEnigma = new Enigma.Builder() //
				.addCable('A', 'V') //
				.addCable('B', 'S') //
				.addCable('C', 'G') //
				.addCable('D', 'L') //
				.addCable('F', 'U') //
				.addCable('H', 'Z') //
				.addCable('I', 'N') //
				.addCable('K', 'M') //
				.addCable('O', 'W') //
				.addCable('R', 'X') //
				.setRotor1(Rotor.getRotorV(), 12, 'D') //
				.setRotor2(Rotor.getRotorIV(), 21, 'S') //
				.setRotor3(Rotor.getRotorII(), 2, 'L') //
				.setReflector(Reflector.getReflectorB()) //
				.build();	
		
		assertEquals(code, encodeEnigma.decode(text));
		assertEquals(text, decodeEnigma.decode(code));
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
		
		final Enigma encodeEnigma = new Enigma.Builder() //
				.addCable('A', 'N') //
				.addCable('E', 'Z') //
				.addCable('H', 'K') //
				.addCable('I', 'J') //
				.addCable('L', 'R') //
				.addCable('M', 'Q') //
				.addCable('O', 'T') //
				.addCable('P', 'V') //
				.addCable('S', 'W') //
				.addCable('U', 'X') //
				.setRotor1(Rotor.getRotorVIII(), 13, 'V') //
				.setRotor2(Rotor.getRotorVI(), 8, 'Z') //
				.setRotor3(Rotor.getRotorIII(), 1, 'U') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		final Enigma decodeEnigma = new Enigma.Builder() //
				.addCable('A', 'N') //
				.addCable('E', 'Z') //
				.addCable('H', 'K') //
				.addCable('I', 'J') //
				.addCable('L', 'R') //
				.addCable('M', 'Q') //
				.addCable('O', 'T') //
				.addCable('P', 'V') //
				.addCable('S', 'W') //
				.addCable('U', 'X') //
				.setRotor1(Rotor.getRotorVIII(), 13, 'V') //
				.setRotor2(Rotor.getRotorVI(), 8, 'Z') //
				.setRotor3(Rotor.getRotorIII(), 1, 'U') //
				.setReflector(Reflector.getReflectorB()) //
				.build();
		
		assertEquals(code, encodeEnigma.decode(text));
		assertEquals(text, decodeEnigma.decode(code));
	}
	
}
