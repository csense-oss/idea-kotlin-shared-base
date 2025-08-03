## 0.2.0
- now uses ideaIC-2024.3 (allows K2 mode)
  - removed old dead methods related to k2 (they just throws....)
- (some) translations for color panel (zh_CN)
- started on catching processCancellationExceptions
- type resolution deprecations fixed 

## 0.1.70

- bump to kotlin 2.0
- added KotlinFullClassNameIndexWrapper
- updated resolution of KtClass from name
- bump idea to 223
- added sources to release
- fixed a few bugs
- improved isSubTypeOfAny
- added anyByFqNames
- added containsNotToken

## 0.1.65

- re-upload of 0.1.64

## 0.1.64

- added cachedFileInMemory

## 0.1.63

- more extension
- better thrown expression evaluation

## 0.1.62

- bump of deps
- published to maven

## 0.1.61

- fixed issues with type alias in resolving isSubTypeOfAny
- also added more operations on KtPsiClass (allFqNames())

## 0.1.60

- kotlin 1.9.22
- added a lot of methods and removed some unnused stuff and moved alot around.
- should resolve types much better

## 0.1.50

- uses IDEA 213 as base now
- added restartLineMarkers
- kotlin 1.8.0
- updated resolveRealType and renamed it to resolveFirstClassType
- added
    - PsiElement.endOffset
    - PsiElement.startOffset
- improved isInTestModule drastically
- isNumberType should be more "stable"
- added resolveType (which does not use on !!)

## 0.1.41

- improved Module.isTestModule()
- added tryNavigate (for PsiElement and for Navigateable)
- other minor functions
- fix sources not included by default / visible in IDEA

## 0.1.40

- attempt at sources
- added resolveRealType
- added toUExceptionClass

## 0.1.30

- reupload

## 0.1.23

- updated KtExpression.isConstant & KtProperty.isGetterConstant
- updated KtExpression.resolvePotentialArgumentName
- added
    - KtBinaryExpression.isEqual
    - KtBinaryExpression.isNotEqual
    - KtCallExpression.findArgumentNames
    - KtClassOrObject.getAllClassProperties
    - KtElement.isInObject
    - KtExpression.isTypeReference
    - KtNamedFunction.isReceiver
    - KtNameReferenceExpression.isMethodReference

## 0.1.22

- updated deps

## 0.1.21

- more extensions on KtNamedFunction

## 0.1.20

- fixes and type alias handling
- runtime exception detection code

## 0.1.19

- resolve & type aliases

## 0.1.18

- findValidProblemElement extension and changed registerProblemSafe to use it instead of hiding exception(s)

## 0.1.17

- more extensions and major improvements to resolve (ktTypeReference)

## 0.1.16

- improved KtExpression.isConstant & KtProperty.isGetterConstant

## 0.1.15

- isTestModule improved

## 0.1.14

- more extensions and some minor naming updated

## 0.1.13

- fixed issue with doesCallSuperFunction

## 0.1.12 (since 0.1.11 had an issue)

- fixed infinite recursive loop in findOverridingImpl

## 0.1.10

- improved findInvocationArgumentName (now looks at more types of expressions)

## 0.1.9

- improved resolveTypeClass for KtParamter (fixes issue with issubtypeof)

## 0.1.8

- fixed a bug with getAllAnnotations (combining lists)

## 0.1.7

- updates to UClass handling of annotations

## 0.1.6

- started on KtPropertyAccessor.findOverridingImpl
- minor update to superClass

## 0.1.5

- more work on MPP annotations (so now pure kotlin annotations that cannot become UAST should somewhat work.)
- more type reference resolving (can fix some work on superclass analysis for Kotlin)

## 0.1.4

- some more on types and parameters (inline eg)

## 0.1.3

- more extensions

## 0.1.2

- added computeTypeAsString for KtExpression
- added convertToBlockFunction for KtNamedFunction

## 0.1.1

- fix project setup (dependencies)

## 0.1.0

- added addFirstInScope & addLastInScope to KtBlockExpression

## 0.0.9

- added findOverridingImpl & doesCallSuperFunction to KtNamedFunction

## 0.0.8

- cleaning up
- class annotations cache updated

## 0.0.7

- thinning out in the new additions.

## 0.0.6

- even more stuff that was missing..

## 0.0.5

- more extensions & bll.
- more refactoring

## 0.0.4

- more extensions & bll.

## 0.0.3

- migrate a lot of extensions from plugin code

## 0.0.2

- change group id to follow regular style

## 0.0.1

- test release.