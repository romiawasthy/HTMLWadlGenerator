HTMLWadlGenerator
=================
HTMLWadlGenerator is an extension of cxf WadlGenerator that provide a HTML view of the WADL.

To use this generator,  add the jar to the classpath and register jaxrs:provider with jaxrs:server. 

To see the output, give these request parameters to any resource url,  ?_wadl&_type=text/html
