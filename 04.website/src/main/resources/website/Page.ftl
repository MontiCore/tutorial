<!--
 @param ast  ASTPage
-->
${tc.signature("website")}
<!DOCTYPE html>
<html lang="en">
<head>
  <link rel="stylesheet" href="main.css">
  <title>${ast.getName()}</title>
</head>
<body>

<div class="wrapper">
<!-- TODO -->
    <h1>${ast.getName()}</h1>
    <#if ast.isPresentTitle()>
      <header>${ast.getTitle().getStringLiteral().getSource()}</header>
    </#if>
  <div class="row">
  <nav class="col">
<#if website.isPresentNavigation()>
<ul>
    <#list website.getNavigation().getNavigationElementList() as navElem>
      ${tc.include("website.NavigationElement", navElem)}
    </#list>
</ul>
</#if>
</nav>

    <section class="col">
<#list ast.getDirectory().getDirectoryElementList() as dirElem>
  ${tc.include("website.DirectoryElement", dirElem)}
</#list>
    </section>
  </div>
</body>
</html>