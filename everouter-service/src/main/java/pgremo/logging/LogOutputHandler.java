package pgremo.logging;

import pgremo.logging.beans.LevelPropertyEditor;
import pgremo.logging.beans.ObjectInstancePropertyEditor;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.*;

import static java.beans.PropertyEditorManager.registerEditor;
import static java.util.logging.ErrorManager.*;
import static java.util.logging.Level.INFO;
import static java.util.logging.LogManager.getLogManager;
import static pgremo.beans.BeanConfigurator.configure;

public class LogOutputHandler extends Handler {

    static {
        registerEditor(Level.class, LevelPropertyEditor.class);
        registerEditor(Filter.class, ObjectInstancePropertyEditor.class);
        registerEditor(Formatter.class, ObjectInstancePropertyEditor.class);
    }

    private Writer out = new OutputStreamWriter(System.out);
    private Writer err = new OutputStreamWriter(System.err);
    private boolean doneHeader;

    public LogOutputHandler() throws InvocationTargetException, IllegalAccessException, IntrospectionException {
        super();
        configure(this, x -> getLogManager().getProperty(getClass().getName() + "." + x.getName()));
    }

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record)) return;
        if (!doneHeader) {
            String header = getFormatter().getHead(this);
            for (Writer writer : new Writer[]{out, err}) {
                try {
                    writer.write(header);
                } catch (IOException e) {
                    reportError(null, e, WRITE_FAILURE);
                }
            }
            doneHeader = true;
        }
        String msg;
        try {
            msg = getFormatter().format(record);
        } catch (Exception ex) {
            reportError(null, ex, FORMAT_FAILURE);
            return;
        }
        try {
            if (record.getLevel().intValue() <= INFO.intValue()) {
                out.write(msg);
            } else {
                err.write(msg);
            }
        } catch (Exception ex) {
            reportError(null, ex, WRITE_FAILURE);
            return;
        }
        flush();
    }

    @Override
    public void flush() {
        for (Writer writer : new Writer[]{out, err}) {
            try {
                writer.flush();
            } catch (IOException e) {
                reportError(null, e, FLUSH_FAILURE);
            }
        }
    }

    @Override
    public void close() throws SecurityException {
        flush();
    }

    @Override
    public synchronized void setEncoding(String encoding) throws SecurityException, UnsupportedEncodingException {
        super.setEncoding(encoding);
        flush();
        out = encoding == null
                ? new OutputStreamWriter(System.out)
                : new OutputStreamWriter(System.out, encoding);
        err = encoding == null
                ? new OutputStreamWriter(System.err)
                : new OutputStreamWriter(System.err, encoding);
    }
}
