# JDF Library
CIP4 JDF Library

## Issue Tracking
Don't write issues, provide Pull-Requets!

## Development Notes
### Release a new Version

```bash
$ git tag -a JDFLibJ-2.1.[VERSION] -m "[TITLE]"
$ git push origin JDFLibJ-2.1.[VERSION]
```

In case a build has been failed, a tag can be deleted using the following command:
```bash
$ git tag -d JDFLibJ-2.1.[VERSION]
$ git push origin :refs/tags/JDFLibJ-2.1.[VERSION]
```
