//MENU
//vytvorit strom menu
// spustit menu nad stromem
//spustit menu nad jednotlivymi vetvemi
// vyvolat spravnou akci v menu 
// po vyvolani akce aktualizovat strom
// kazdou zmenu zapsat na disk do projektoveho souboru



//EDITOR
//otevreni editoru
//zapani zmen do souboru



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