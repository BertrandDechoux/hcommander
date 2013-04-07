HCommander
=================

Most Hadoop jobs (mapreduce or not) are launched from command line. As a consequence, building a clean command line parser is a must. [JCommander](http://jcommander.org) does almost everything you would ever want. But it does not know, out of the box, about the Hadoop world in general and about [ToolRunner](http://hadoop.apache.org/docs/current/api/org/apache/hadoop/util/ToolRunner.html) in particular. HCommander bridges the gab for you.

At the moment, you will need to build the package yourself.

```java
public static void main(String[] args) throws Exception {
	Tool hCommander = new HCommander(getCommands());
	int resultCode = ToolRunner.run(hCommander, args);
	System.exit(resultCode);
}
```

This allows to create a user friendly command line tool.
Verifying parameters, displaying full help :

```
Usage: [options] [command] [command options]
  Commands:
    test        <no description>
    help        Print usage of a selected command.

For more information about one command, use the 'help -about [command]' command.
```

or command specific help :

```
Print usage of a selected command.
Usage: help [options]
  Options:
    -about
       Name of the command for which help is required.
```
