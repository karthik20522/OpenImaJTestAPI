import AssemblyKeys._ // put this at the top of the file
 
assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("javax", "xml", xs @ _*) => MergeStrategy.first
    case PathList("org", "apache", xs @ _*) => MergeStrategy.first
    case PathList("org", "objenesis", xs @ _*) => MergeStrategy.first
    case PathList("org", "w3c", "dom", xs @ _*) => MergeStrategy.first
    case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.first
    case PathList("org", "xmlpull", xs @ _*) => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case x => old(x)
  }
}


//-collections-2.1.jar:org/apache/commons/collections/ArrayStack.class
//.jar:javax/xml/XMLConstants.class