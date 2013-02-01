//MENU
// kazdou zmenu zapsat na disk do projektoveho souboru



//EDITOR
//zapsani zmen do souboru
//editor ma dve okna - zadost/odpoved - kazde okno ma dva panely textovy rezim a klikaci rezim
//editor ma toolbox a popupmenu s operacema


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