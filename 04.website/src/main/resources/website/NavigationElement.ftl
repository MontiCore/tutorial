<li><a href="${ast.getName()}.html">${ast.getName()}</a></li>
<ul>
    <#list ast.getNavigationElementList() as navElem>
        ${tc.include("website.NavigationElement", navElem)}
    </#list>
</ul>
