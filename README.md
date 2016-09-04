# Vendor-specific MagicDraw application package ("no_install.zip") from NoMagic, Inc.

This package is to upload a MagicDraw "no_install.zip" package to 
[tiwg/org.omg.tiwg.vendor.nomagic @ bintray](https://bintray.com/tiwg/org.omg.tiwg.vendor.nomagic/com.nomagic.magicdraw.package).

## Upload workflow.

1. [Download MagicDraw](https://www.magicdraw.com/download/magicdraw)

- Select the "no_install.zip" package

- Destination folder: `./downloads` (this folder is ignored in github)

2. Split the MD download

```
cd downloads
../scripts/splitMagicDrawPackage.sh <md no_install.zip>
```

This creates a sub-directory: `./downloads/parts`; e.g:

```
$ ../scripts/splitMagicDrawPackage.sh MagicDraw_180_sp6_no_install.zip 
2 zip files will be made (100% efficiency)
creating: parts/MagicDr1.zip
creating: parts/MagicDr2.zip
248M -rw-rw-r--. 1 rouquett jplissa 248M Sep  6 03:25 parts/MagicDraw_180_sp6_no_install.part1.zip
192M -rw-rw-r--. 1 rouquett jplissa 192M Sep  6 03:25 parts/MagicDraw_180_sp6_no_install.part2.zip
```

3. Upload to bintray.com

This requires `local.credentials.sbt` to have something like this:

```
credentials += Credentials("Bintray API Realm", "api.bintray.com", "<bintray user>", "<bintray api key>")
```

Then, execute:

```
sbt publish
```

Note that the artifacts are uploaded to bintray and are only resolvable privately using the bintray credentials.
This is particularly useful to perform some testing before publishing the files publicly.

4. Testing (using uploaded artifacts)
  
This requires `local.credentials.sbt` to have something like this:

```
credentials += Credentials("Bintray", "dl.bintray.com", "<bintray user>", "<bintray api key>")
```

5. Publish

Make the uploaded files publicly accessible without any credentials.

- Install the [JFrog CLI](https://www.jfrog.com/getcli/)

- Configure the JFrog CLI with Bintray credentials
    
    ```
    jfrog bt c
    ```
    
    Follow the prompts; for more details, see: https://bintray.com/docs/usermanual/cli/cli_jfrogcli.html#_configuration

- Publish the uploaded artifact files for the MD package version

  Example:
  
  ```
  jfrog bt vp  tiwg/org.omg.tiwg.vendor.nomagic/com.nomagic.magicdraw.package/18.0-sp6
  ```