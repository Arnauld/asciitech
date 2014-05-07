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

Licenses

* GRAL uses the ​GNU Lesser General Public License (LGPL v3)
* jlatexmath [license](http://forge.scilab.org/index.php/p/jlatexmath/source/tree/master/LICENSE)


Maven Local Repository
----------------------

    mvn install:install-file  -Dfile=tmp/ditaa-0.9.jar \
                              -DgroupId=ditaa \
                              -DartifactId=ditaa \
                              -Dversion=0.9\
                              -Dpackaging=jar \
                              -DlocalRepositoryPath=repo/

    mvn install:install-file  -Dfile=tmp/gral-core-0.10.jar \
                              -DgroupId=de.erichseifert \
                              -DartifactId=gral \
                              -Dversion=0.10\
                              -Dpackaging=jar \
                              -DlocalRepositoryPath=repo/

    mvn install:install-file  -Dfile=tmp/VectorGraphics2D-0.9.1.jar \
                              -DgroupId=de.erichseifert \
                              -DartifactId=VectorGraphics2D \
                              -Dversion=0.9.1\
                              -Dpackaging=jar \
                              -DlocalRepositoryPath=repo/