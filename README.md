# NoMagic, Inc. MagicDraw Application Package

This package currently only provides a pom with an extra attribute with a URL as a value that points to a download location for the NoMagic MagicDraw application package ("no_install.zip").

## Usage

The extra attribute is called `md.core`. It can be found in the `properties` section of the pom:

``
<properties>
    <md.core>http://webdev.nomagic.com/noinstall/magicdraw/ltr/latest</md.core>
</properties>
``
