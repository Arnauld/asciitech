Dependencies
------------

    <properties>
        <asciitech.version>0.0.5</asciitech.version>
    </properties>

Library dependency:

    <dependency>
        <groupId>org.technbolts</groupId>
        <artifactId>asciitech</artifactId>
        <version>${asciitech.version}</version>
    </dependency>

3rd Party dependencies ([gral](http://trac.erichseifert.de/gral/), [ditaa](http://ditaa.sourceforge.net/),
[jlatexmath](http://forge.scilab.org/index.php/p/jlatexmath/)):

    <dependency>
        <groupId>org.technbolts</groupId>
        <artifactId>asciitech</artifactId>
        <classifier>third-parties</classifier>
        <version>${asciitech.version}</version>
    </dependency>


3rd Party Dependencies
----------------------

* [gral](http://trac.erichseifert.de/gral/): GRAL is a free Java library for displaying plots
  (graphs, diagrams, and charts). The acronym GRAL simply stands for GRAphing Library.
* [ditaa](http://ditaa.sourceforge.net/): ditaa is a small command-line utility written in Java,
  that can convert diagrams drawn using ascii art into proper bitmap graphics.
* [jlatexmath](http://forge.scilab.org/index.php/p/jlatexmath/): JLaTeXMath is a Java library.
  Its main purpose is to display mathematical formulas written in LaTeX.