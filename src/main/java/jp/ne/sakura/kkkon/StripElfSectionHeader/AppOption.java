/*
 * The MIT License
 * 
 * Copyright (C) 2013 Kiyofumi Kondoh
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package jp.ne.sakura.kkkon.StripElfSectionHeader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 *
 * @author Kiyofumi Kondoh
 */
class AppOption
{
    public boolean batchRun = false;
    public boolean dryRun = false;
    public boolean keepBackup = true;
    public String output = null;
    public boolean recursive = false;
    public boolean verbose = false;
    public String[] args = null;

    protected Options options = null;
    protected CommandLine commandLine = null;

    public void createOptions()
    {
        Options opts = new Options();

        {
            Option o = new Option("B", "batch", false, "batch mode. non interactive.");
            opts.addOption( o );
        }
        {
            Option o = new Option(null, "dry-run", false, "dry run");
            opts.addOption( o );
        }
        {
            Option o = new Option(null, "no-keep", false, "no keep backup");
            opts.addOption( o );
        }
        {
            Option o = new Option("o", "output", true, "output file or directory" );
            o.setArgName("dest");
            opts.addOption( o );
        }
        opts.addOption("r", "recursive", false, "recursive directory" );
        opts.addOption("v", "verbose", false, "verbose display" );

        this.options = opts;
    }

    public void showUsage()
    {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "strip-elf-section-header", this.options );
    }
    public boolean parseOption( final String args[] )
    {
        CommandLineParser parser = new PosixParser();

        CommandLine cmdLine = null;
        try {
            cmdLine = parser.parse( this.options, args );
        } catch (ParseException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ( null == cmdLine )
        {
            return false;
        }

        this.commandLine = cmdLine;
        return true;
    }
    public boolean applyOption()
    {
        if ( null == this.commandLine )
        {
            return false;
        }

        {
            this.batchRun = false;
            if ( this.commandLine.hasOption("batch") )
            {
                this.batchRun = true;
            }
        }
        {
            this.dryRun = false;
            if ( this.commandLine.hasOption("dry-run") )
            {
                this.dryRun = true;
            }
        }
        {
            this.keepBackup = true;
            if ( this.commandLine.hasOption("no-keep") )
            {
                this.keepBackup = false;
            }
        }
        {
            this.output = this.commandLine.getOptionValue("o");
        }
        {
            this.recursive = false;
            if ( this.commandLine.hasOption("r") )
            {
                this.recursive = true;
            }
        }
        {
            this.verbose = false;
            if ( this.commandLine.hasOption("verbose") )
            {
                this.verbose = true;
            }
        }

        this.args = this.commandLine.getArgs();

        return true;
    }
}

