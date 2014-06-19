HTMLWadlGenerator
=================
HTMLWadlGenerator is an extension of cxf WadlGenerator that provide a HTML view of the WADL.

To use this generator,  add the jar to the classpath and configure a provider in jaxrs:server definition. 

<jaxrs:providers>
<bean class=" com.rest.cxf.wadl.HTMLWadlGenerator"/>
