
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `SYSROLE`
-- ----------------------------
DROP TABLE IF EXISTS `SYSROLE`;
CREATE TABLE `SYSROLE` (
 		`ROLE_ID` varchar(100) NOT NULL,
		`id` int(
Expression var[5] is undefined on line 12, column 35 in mysql_SQL_Template.ftl.
The problematic instruction:
----------
==> ${var[5]} [on line 12, column 33 in mysql_SQL_Template.ftl]
----------

Java backtrace for programmers:
----------
freemarker.core.InvalidReferenceException: Expression var[5] is undefined on line 12, column 35 in mysql_SQL_Template.ftl.
	at freemarker.core.TemplateObject.assertNonNull(TemplateObject.java:124)
	at freemarker.core.Expression.getStringValue(Expression.java:118)
	at freemarker.core.Expression.getStringValue(Expression.java:93)
	at freemarker.core.DollarVariable.accept(DollarVariable.java:76)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.IfBlock.accept(IfBlock.java:82)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.IteratorBlock$Context.runLoop(IteratorBlock.java:172)
	at freemarker.core.Environment.visit(Environment.java:351)
	at freemarker.core.IteratorBlock.accept(IteratorBlock.java:95)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.MixedContent.accept(MixedContent.java:92)
	at freemarker.core.Environment.visit(Environment.java:196)
	at freemarker.core.Environment.process(Environment.java:176)
	at freemarker.template.Template.process(Template.java:232)
	at com.aiw.util.Freemarker.printFile(Freemarker.java:55)
	at com.aiw.util.CodeUtil.main(CodeUtil.java:83)
