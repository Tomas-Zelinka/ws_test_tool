//GUI

//EDITOR
//zapsani zmen do souboru
//tretina obrazovky pro navigator
//dve tretiny pro monitory a editory
//proxy monitor - vrch monitoru zachycene pozadavky a dole jsou request a response se zmenou viz predchozi prace
//test list - vrch bude zobrazen list s vlastnostma a statistikama a oznacenim ktere spustit - timhle se bude spoustet
//			  testovaci jednotka pri kliknuti na test se dole zobrazi pozadavek a odpoved			  
//test editor - po kliknuti na test se nacte do editoru - editor bude mit zalozky http request, fault injection a settings 
//remote control - bude kontrolovat vzdalene jednotky, odesilat testlisty s daty, spoustet testy a prijimat vysledky
//cli			 - bude spoustet test listy - bude moct spustit vic listu - jeden suite muze mit vic listu 


//Project 
//muze mit vice testovacich sad
//bude mit soubor s nastavenim, ktery bude slouzit k identifikaci adresare, ze jde prave o projekt
//programu FIWS

//Testovaci sada 
//muze mit mit vice testovacich pripadu a bude slouzit k jejich vyhodnocovani a porovnavani
//bude mit parametry a dvojim poklikem se otevre tabulka s testovacimi pripady 
// zde bude moci byt spustena testovaci jednotka pro vybrane nebo vsechny tesovaci pripady

//testovaci sada bude mit parametr, kde budou uvedeny testovaci pripady ktere budou predany testovaci
//jednotce ke zpracovani. Zbyle parametry budou nastaveny u jednotlivych testovacich pripadu
//tento vyber bude persitetni proto, aby se mohla data predat testovaci jednotce i z CLI

// Testovaci pripad
// bude sestaven z http pozadavku a mistem pro odpoved, pripadne jiz obdrezene odpovedi
// bude mit parametry a dvojim poklikem se otevere editor pro http pozadavek a odpoved
// zde bude moci byt spustena testovaci jednotka nad jednotlivym testovacim pripadem





// redirecting outputs

/*  text area version
 private void updateTextArea(final String text) {
  SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      textArea.append(text);
    }
  });
}
 
private void redirectSystemStreams() {
  OutputStream out = new OutputStream() {
    @Override
    public void write(int b) throws IOException {
      updateTextArea(String.valueOf((char) b));
    }
 
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      updateTextArea(new String(b, off, len));
    }
 
    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
  };
 
  System.setOut(new PrintStream(out, true));
  System.setErr(new PrintStream(out, true));
}


///////////////// verze pro jtextpane


private void updateTextPane(final String text) {
  SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      Document doc = textPane.getDocument();
      try {
        doc.insertString(doc.getLength(), text, null);
      } catch (BadLocationException e) {
        throw new RuntimeException(e);
      }
      textPane.setCaretPosition(doc.getLength() - 1);
    }
  });
}
 
private void redirectSystemStreams() {
  OutputStream out = new OutputStream() {
    @Override
    public void write(final int b) throws IOException {
      updateTextPane(String.valueOf((char) b));
    }
 
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      updateTextPane(new String(b, off, len));
    }
 
    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
  };
 
  System.setOut(new PrintStream(out, true));
  System.setErr(new PrintStream(out, true));
}





*/