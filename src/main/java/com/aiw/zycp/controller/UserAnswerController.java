package com.aiw.zycp.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiw.api.base.controller.BaseController;
import com.aiw.entity.KeyValue;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.zycp.entity.Answer;
import com.aiw.zycp.mapper.AnswerMapper;
import com.aiw.zycp.util.HttpsClient;
import com.aiw.zycp.util.Mail;
import com.aiw.zycp.util.QuestionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller(value="UserAnswerController")
@RequestMapping(value="/zycp/user")
public class UserAnswerController extends BaseController<AnswerMapper, Answer>{
   
	

    @RequestMapping(value = "/getquestion")
    public String getQuestion(@ModelAttribute Answer t){
       
        return "zycp/"+ t.getType();
    }
    
    
    @RequestMapping(value = "/wx")
    @ResponseBody
    public SysResult wx() throws JsonParseException, JsonMappingException, IOException{
        String code = request.getParameter("code");
        String rs = HttpsClient.getSessionKey(code);
        ObjectMapper ob = new ObjectMapper();
        Map map = ob.readValue(rs.getBytes(), Map.class);
        return SysResult.oK(map);
    }
    
    
    @RequestMapping(value = "/answer")
    public String answer(@ModelAttribute Answer t){
	    
	    
	    String questionvalue = request.getParameter("questionvalue");
	    t.setAnswer(questionvalue);
        final String email = request.getParameter("email");
        String type = request.getParameter("type");
        int i = mapper.insert(t);
        logger.debug("update " + i);
        
        if(type.equals("mbti")){
            String rs =  "";
            
//          外向（E）和内向（I）
//          感觉（S）和直觉（N）
//          思考（T）和情感（F）
//          判断（J）和知觉（P）
            
            if(containCount('E',questionvalue)>=containCount('I',questionvalue)){
                rs = rs + "E";
            }else{
                rs = rs + "I";
            }
            
            if(containCount('S',questionvalue)>=containCount('N',questionvalue)){
                rs = rs + "S";
            }else{
                rs = rs + "N";
            }
            
            if(containCount('T',questionvalue)>=containCount('F',questionvalue)){
                rs = rs + "T";
            }else{
                rs = rs + "F";
            }
            
            if(containCount('J',questionvalue)>=containCount('P',questionvalue)){
                rs = rs + "J";
            }else{
                rs = rs + "P";
            }
            final String desc = getDesc(rs);
            request.setAttribute("rs", rs);
            request.setAttribute("desc", desc);
            final String rss = rs;
            new Thread(){
                public void run(){
                    Mail mail = new Mail();  
                    mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
                    mail.setSender("babyzn@126.com");  
                    mail.setReceiver(email); // 接收人  
                    mail.setUsername("babyzn@126.com"); // 登录账号,一般都是和邮箱名一样吧  
                    mail.setPassword("126wsf0913"); // 发件人邮箱的登录密码  
                    mail.setSubject("MBTI职业性格测试(精简版)结果,你属于"+rss+"类型");  
                    mail.setMessage(desc);  
                    send(mail);  
                }
            }.start();
            return "zycp/rs";
            
        }else if(type.equals("pdp")){
            String desc =  "";
            String rs = "";
            if(questionvalue.equals("0")){
                rs="老虎型 (支配型Dominance) ";
                desc=desc+"总结：“老虎”一般企图心强烈，喜欢冒险，个性积极，竞争力强，凡事喜欢掌控全局发号施令，不喜欢维持现状，但行动力强，目标一经确立便会全力以赴。它的缺点是在决策上较易流于专断，不易妥协，故较容易与人发生争执摩擦。如果会员中有“老虎”要给予他更多的挑战，他会觉得自己有价值，教练给他上课时候注意结果为导向，教练要在他面前展示自信果断的一面，同时避免在公众场合与他唱反调。中外名人中毛泽东、朱熔基以及前英国首相撒切尔夫人为较典型的老虎型，德国为老虎型人数最多的国家。";
                desc=desc+"个性特点：有自信，够权威，决断力高，竞争性强，胸怀大志，喜欢评估。企图心强烈，喜欢冒险，个性积极，竞争力强，有对抗性。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"优点：善于控制局面并能果断地作出决定的能力；往往此类会员锻炼成果非常显著。";
                desc=desc+"缺点：当感到健身的压力时，这类人就会太重视迅速的完成健身目标，就容易忽视细节，他们可能不顾自己和别人的情感。由于他们要求过高，加之好胜的天性，有时会成为工作狂或者是锻炼狂。";
                desc=desc+"老虎型健身风格的主要行为：";
                desc=desc+"交谈时进行直接的目光接触；";
                desc=desc+"锻炼时候有目的性且能迅速行动；";
                desc=desc+"说话快速且具有说服力；";
                desc=desc+"运用直截了当的实际性语言；";
                desc=desc+"办公室挂有日历、计划要点。";
                desc=desc+"<br><br>";
                desc=desc+"老虎性格，具备高支配型特质，竞争力强、好胜心盛、积极自信，是个有决断力的组织者。他胸怀大志、勇于冒险、分析敏锐，主动积极且具极为强烈的企图心，只要认定目标就勇往直前，不畏反抗与攻讦，誓要取得目标的家伙。";
                desc=desc+"老虎型会员都倾向以权威作风来进行决策，当其教练除了要尊重会员的选择之外，也要有冒险犯难的勇气，为会员的健身目标一起做”拼命三郎”教练";
                desc=desc+"宏碁集团的施振荣和前美国GE总裁韦尔奇 (Jack Welch)等，都是老虎型领导人。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
            }else if(questionvalue.equals("1")){
                rs="孔雀型(表达型Extroversion)";
                desc=desc+"总结：“孔雀”热情洋溢，好交朋友，口才流畅，重视形象，擅于人际关系的建立，富同情心，和容易和人接近。缺点是容易过于乐观，往往无法估计细节，在执行锻炼计划时候需要专业度极高的教练配合和指导。对孔雀要以鼓励为主给他表现机会保持他的锻炼激情，但也要注意他的情绪化和防止细节失误。孙中山、克林顿、里根、戈尔巴乔夫都是这一类型的人，美国是孔雀型人最多的国家。";
                desc=desc+"个性特点：很热心，够乐观，口才流畅，好交朋友，风度翩翩，诚恳热心。热情洋溢、好交朋友、口才流畅、个性乐观、表现欲强。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"优点：此类型的人生性活泼。能够使人兴奋，他们高效地锻炼，善于建立同盟或搞好关系来实现锻炼目标。他们很适合需要当众表现、引人注目的健身房锻炼";
                desc=desc+"缺点：因其跳跃性的思考模式，常无法顾及细节以及对健身计划的完成执着度";
                desc=desc+"孔雀型健身者风格的主要行为：";
                desc=desc+"运用快速的手势；";
                desc=desc+"面部表情特别丰富；";
                desc=desc+"运用有说服力的语言；";
                desc=desc+"时时能给教练很多惊喜和鼓舞人心的东西，很有创造力";
                desc=desc+"孔雀百利具有高度的表达能力，他的社交能力极强，有流畅无碍的口才和热情幽默的风度，在团体或社群中容易广结善缘、建立知名度。孔雀型天生具备乐观与和善的性格，有真诚的同情心和感染他人的能力，在以团队合作为主的工作环境中，会有最好的表现。";
                desc=desc+"孔雀型会员在任何场合，都是人缘最好的人和最受欢迎的人，是最能吹起领导号角的人物。";
                desc=desc+"当孔雀型会员的教练，除要能乐于和他交流和指导外，，还要对其谦逊得体，不露锋、不出头，把一切成功光华都让给该会员。";
                desc=desc+"孔雀型会员，不宜有个老虎型教练。";
                desc=desc+"反之，若老虎型会员，有个孔雀型教练，则会是最佳搭配。孔雀型的人天生具有鼓吹理想的特质，在推动新思维、执行某种新使命或推广某项宣传等任务的工作中，都会有极出色的表现。";
                desc=desc+"有台湾企管大师之称的石滋宜博士，就是属于孔雀型的人。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
            
            }else if(questionvalue.equals("2")){
                rs="考拉型 (耐心型Pace/Patience) ";
                desc=desc+"总结：“考拉”属于行事稳健，不会夸张强调平实的人，性情平和对人不喜欢制造麻烦，不兴风作浪，温和善良，在教练眼中常让人误以为是懒散不积极，但只要决心投入，绝对是“路遥知马力”的最佳典型。印度的甘地、蒋经国、宋庆龄都是此类型的人，一般而言，宗教信仰者都是“考拉”，而中国正是考拉型最多的摇篮。";
                desc=desc+"个性特点：很稳定，够敦厚，温和规律，不好冲突。行事稳健、强调平实，有过人的耐力，温和善良。";
                desc=desc+"<br><br>";
                desc=desc+"优点：他们对其他人的感情很敏感，这使他们在集体环境中左右逢源。";
                desc=desc+"缺点：很难坚持自己的观点和迅速做出决定。一般说来，他们不喜欢争执，他们不愿处理争执。";
                desc=desc+"考拉型健身者风格的主要行为：";
                desc=desc+"面部表情和蔼可亲；";
                desc=desc+"说话慢条斯理，声音轻柔；";
                desc=desc+"用赞同型、鼓励性的语言；";
                desc=desc+"钱包里放着家人的照片。";
                desc=desc+"考拉具有高度的耐心。他敦厚随和，行事冷静自持；生活讲求规律但也随缘从容，面对困境，都能泰然自若。";
                desc=desc+"勇于开疆辟土的老虎型教练";
                desc=desc+"配以与人为善的考拉型会员，也是好的搭配。";
                desc=desc+"无尾熊型人强调无为而治，能与周围的人和睦相处而不树敌。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
            }else if(questionvalue.equals("3")){
                rs="猫头鹰型 (精确型Conformity) ";
                desc=desc+"总结：“猫头鹰”传统而保守，分析力强，精确度高是最佳的品质保证者，喜欢把细节条例化，个性拘谨含蓄，谨守分寸忠于职责，但会让人觉得“吹毛求疵”。“猫头鹰”清晰分析道理说服别人很有一套，处事客观合理，只是有时会钻在牛角尖里拔不出来。古代断案如神的包拯（包青天）正是此种类型的典范。日本是这个类型人数较多的国家。";
                desc=desc+"个性特点：很传统，注重细节，条理分明，责任感强，重视纪律。保守、分析力强，精准度高，喜欢把细节条例化，个性拘谨含蓄。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"优点：天生就有爱找出事情真相的习性，因为他们有耐心仔细考察所有的细节并想出合乎逻辑的解决办法。";
                desc=desc+"缺点：把事实和精确度置于感情之前，这会被认为是感情冷漠。在压力下，有时为了避免做出结论，他们会分析过度。";
                desc=desc+"猫头鹰型健身者风格的主要行为：";
                desc=desc+"很少有面部表情；";
                desc=desc+"动作缓慢；";
                desc=desc+"使用精确的语言、注意特殊细节；";
                desc=desc+"猫头鹰具有高度精确的能力，其行事风格，重规则轻情感，事事以规则为准绳，并以之为主导思想。";
                desc=desc+"他性格内敛、善于以数字或规条为表达工具而不大擅长以语言来沟通情感。";
                desc=desc+"他行事讲究条理分明、守纪律重承诺，是个完美主义者";
                desc=desc+"猫头鹰型喜欢在安全的健身房锻炼，且其表现也会最好。其行事讲究制度化，事事求依据和规律的习性，极为适合事务机构的行事方式。";
                desc=desc+"他们尊重传统、重视架构、事事求据喜爱安定的性格，由于他们行事讲究制度化，事事求依据和规律，故会将细节条例化，事事检查以求正确无误，甚至为了办事精确，不惜对人吹毛求疵或挑剔别人的错误，以显现自己一切照章办事的态度和求取完美的精神";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
            }else if(questionvalue.equals("4")){
                rs="变色龙型 (整合型1/2 Sigma) ";
                desc=desc+"总结：“变色龙”中庸而不极端，凡事不执着，韧性极强，擅于沟通是天生的谈判家，他们能充分融入各种新环境新文化且适应性良好，在教练眼中会觉得他们“没有个性”，故“没有原则就是最高原则”，他们懂得凡事看情况看场合。前总理周恩来、美国前国务卿基辛格、诸葛亮都是这种类型。香港和台湾是变色龙较多的地区。很多浙江商人也属于此种性格。在健身中心此类会员也很多。 ";
                desc=desc+"<br><br>";
                desc=desc+"优点：善于在调整自己的角色去适应环境，具有很好的沟通能力。";
                desc=desc+"缺点：从别人眼中看变色龙族群，会觉得他们较无个性及原则。";
                desc=desc+"变色龙型健身者风格的主要行为：";
                desc=desc+"综合老虎、孔雀、考拉、猫头鹰的特质，看似没有凸出个性，但擅长整合各项资源；没有强烈的个人意识形态，是他们处事的价值观。";
                desc=desc+"<br><br>";
                desc=desc+"<br><br>";
                desc=desc+"变色龙具有高度的应变能力。他性格善变，处事极具弹性，能为了适应环境的要求而调整其决定甚至信念。";
                desc=desc+"变色龙型的人，是支配型、表达型、耐心型、精确型四种特质的综合体，没有突出的个性，擅长整合内外信息，兼容并蓄，不会与人为敌，以中庸之道处世。他们处事圆融，弹性极强，处事处处留有余地，行事绝对不会走偏锋极端，是一个办事让你放心的人物。然而，由于他们以善变为其专长，故做人不会有什么立场或原则，也不会对任何人有效忠的意向，是个冯道式的人物。教练会难以忍受其善变和不讲原则的行为。";
                desc=desc+"变色龙型的会员既没有凸出的个性，对事也没有什么强烈的个人意识型态，事事求中立并倾向站在没有立场的位置，故在冲突的环境中，是个能游走折中的高手。由于他们能密切地融合于各种环境中，只要教练制定明确的健身目标，他们都能恰如其分地完成其任务。";
                desc=desc+"<br><br>";
            }
            request.setAttribute("rs", rs);
            request.setAttribute("desc", desc);
            
            final String rss = rs;
            final String descc = desc;
            new Thread(){
                public void run(){
                    Mail mail = new Mail();  
                    mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
                    mail.setSender("babyzn@126.com");  
                    mail.setReceiver(email); // 接收人  
                    mail.setUsername("babyzn@126.com"); // 登录账号,一般都是和邮箱名一样吧  
                    mail.setPassword("126wsf0913"); // 发件人邮箱的登录密码  
                    mail.setSubject("PDP性格测试(精简版)结果,你属于"+rss+"类型");  
                    mail.setMessage(descc);  
                    send(mail);  
                }
            }.start();
            
            return "zycp/rs";
            
        }else if(type.equals("hlt")){
            String desc =  "";
            String rs = "";
            if(questionvalue.equals("0")){
                
                
                rs="常规型（C）";
                desc="常规型（C）：其基本的倾向是顺从、谨慎、保守、实际、稳重、有效率、善于自我控制。他们喜欢从事记录、整理档案资料、操作办公机械、处理数据资料等有系统、有条理的活动，具备文书、算术等能力，适合他们从事的典型职业包括事务员、会计师、银行职员等。";
                
            }else if(questionvalue.equals("1")){
                rs="现实型（R）";
                desc="现实型（R）：其基本的倾向是喜欢以物、机械、动物、工作等为对象，从事有规则的、明确的、有序的、系统的活动。因此，这类人偏好的是以机械和物为对象的技能性和技术性职业。为了胜任，他们需要具备与机械、电气技术等有关的能力。他们的性格往往是顺应、具体、朴实的，社交能力则比较缺乏。";
            
            }else if(questionvalue.equals("2")){
                rs="研究型（I）";
                desc="研究型（I）：其基本的倾向是分析型的、智慧的、有探究心的和内省的，喜欢根据观察而对物理的、生物的、文化的现象进行抽象的、创造性的研究活动。因此，这类人偏好的是智力的、抽象的、分析的、独立的、带有研究性质的职业活动，诸如科学家、医生、工程师等。";   
            }else if(questionvalue.equals("3")){
                rs="企业型（E）";
                desc="企业型（E）：其基本的倾向是喜欢冒险、精力充沛、善于社交、自信心强。他们强烈关注目标的追求，喜欢从事为获得利益而操纵、驱动他人的活动。由于具备优秀的主导性和对人说服、接触的能力，这一类型的人特别适合从事领导工作或企业经营管理的职业。";
            }else if(questionvalue.equals("4")){
                rs="社会型（S）";
                desc="社会型（S）：其基本的倾向是合作、友善、助人、负责任、圆滑、善于社交言谈、善解人意等。他们喜欢社会交往，关心社会问题，具有教育能力和善意与人相处等人际关系方面的能力，适合这一类人的典型的职业有教师、公务员、咨询员、社会工作者等以与人接触为中心的社会服务型的工作。";
            }else if(questionvalue.equals("5")){
                rs="艺术型（A）";
                desc="艺术型（A）：其基本的倾向是具有想象、冲动、直觉、无秩序、情绪化、理想化、有创意、不重实际等特点，他们喜欢艺术性的职业环境，也具备语言、美术、音乐、演艺等方面的艺术能力，擅长以形态和语言来创作艺术作品，而对事务性的工作则难以胜任。文学创作、音乐、美术、演艺等职业特别适合于他们。";
            }
            request.setAttribute("rs", rs);
            request.setAttribute("desc", desc);
            //request.getRequestDispatcher("/rs.jsp").forward(request,response);
            
            final String rss = rs;
            final String descc = desc;
            new Thread(){
                public void run(){
                    Mail mail = new Mail();  
                    mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
                    mail.setSender("babyzn@126.com");  
                    mail.setReceiver(email); // 接收人  
                    mail.setUsername("babyzn@126.com"); // 登录账号,一般都是和邮箱名一样吧  
                    mail.setPassword("126wsf0913"); // 发件人邮箱的登录密码  
                    mail.setSubject("霍兰德职业兴趣测试(精简版)结果,你属于"+rss+"类型");  
                    mail.setMessage(descc);  
                    send(mail);  
                }
            }.start();
            return "zycp/rs";
            
        }else if(type.equals("disc")){
            String desc =  "";
            String rs = "";
            
            Map<String,String> map = QuestionUtil.getDISCRS();
            Iterator<String> it = map.keySet().iterator();
            String key = null;
            while(it.hasNext()){
                key = it.next();
                if(key!=null && key.startsWith(questionvalue)){
                    rs = key;
                    desc = map.get(key);
                }
            }
            request.setAttribute("rs", rs);
            request.setAttribute("desc", desc);
            //request.getRequestDispatcher("/rs.jsp").forward(request,response);
            
            final String rss = rs;
            final String descc = desc;
            new Thread(){
                public void run(){
                    Mail mail = new Mail();  
                    mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
                    mail.setSender("babyzn@126.com");  
                    mail.setReceiver(email); // 接收人  
                    mail.setUsername("babyzn@126.com"); // 登录账号,一般都是和邮箱名一样吧  
                    mail.setPassword("126wsf0913"); // 发件人邮箱的登录密码  
                    mail.setSubject("DISC性格测试题（完整版）结果,你属于"+rss+"类型");  
                    mail.setMessage(descc);  
                    send(mail);  
                }
            }.start();
            return "zycp/rs";
        }else if(type.equals("fpa")){
            String desc =  QuestionUtil.getFPADesc();
            String rs = questionvalue;
            request.setAttribute("rs", rs);
            request.setAttribute("desc", desc);
            //request.getRequestDispatcher("/fpa_rs.jsp").forward(request,response);
            
            final String rss = rs;
            final String descc = desc + rss;
            new Thread(){
                public void run(){
                    Mail mail = new Mail();  
                    mail.setHost("smtp.126.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
                    mail.setSender("babyzn@126.com");  
                    mail.setReceiver(email); // 接收人  
                    mail.setUsername("babyzn@126.com"); // 登录账号,一般都是和邮箱名一样吧  
                    mail.setPassword("126wsf0913"); // 发件人邮箱的登录密码  
                    mail.setSubject("FPA性格色彩试,你的结果是"+rss);  
                    mail.setMessage(descc);  
                    send(mail);  
                }
            }.start();
            return "zycp/fpa_rs";
            
        }
	    
       
        return "";
    }
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    public SysResult get(@PathVariable("id") Integer id){
		return get_p(id);
    }
        
	@Override
    @RequestMapping(value = "")
    public SysResult get(){
    	return get_p();
    }

    @Override
    public SysResult badd() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult bupdate(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult update(Answer t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult delete(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult list(Page page, Answer t) {
        // TODO Auto-generated method stub
        return null;
    }
	
    public int containCount(Character r,String content){
        char[] charArray = content.toCharArray();
        int i = 0;
        for (char c : charArray) {
            if(c == r){
                i++;
            }
        }
        return i;
    }
    
    
    public boolean send(Mail mail) {
        // 发送email
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(mail.getHost());
            // 字符编码集的设置
            email.setCharset(Mail.ENCODEING);
            // 收件人的邮箱
            email.addTo(mail.getReceiver());
            email.setSSLOnConnect(true);
            // 发送人的邮箱
            email.setFrom(mail.getSender(), mail.getName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(mail.getUsername(), mail.getPassword());
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg(mail.getMessage());
            // 发送
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public String getDesc(String type){
        String des = "";
        if(type.equals("ISTJ")){
            des=des+"ISTJ<br>";
            des=des+"1.严肃、安静、藉由集中心志与全力投入、及可被信赖获致成功。<br>";
            des=des+"2.行事务实、有序、实际、逻辑、真实及可信赖。<br>";
            des=des+"3.十分留意且乐于任何事（工作、居家、生活均有良好组织及有序）。<br>";
            des=des+"4.负责任。<br>";
            des=des+"5.照设定成效来作出决策且不畏阻挠与闲言会坚定为之。<br>";
            des=des+"6.重视传统与忠诚。<br>";
            des=des+"7.传统性的思考者或经理。<br>";
        }else if(type.equals("ISFJ")){
            des=des+"ISFJ<br>";
            des=des+"1.安静、和善、负责任且有良心。<br>";
            des=des+"2.行事尽责投入。<br>";
            des=des+"3.安定性高，常居项目工作或团体之安定力量。<br>";
            des=des+"4.愿投入、吃苦及力求精确。<br>";
            des=des+"5.兴趣通常不在于科技方面。对细节事务有耐心。<br>";
            des=des+"6.忠诚、考虑周到、知性且会关切他人感受。<br>";
            des=des+"7.致力于创构有序及和谐的工作与家庭环境。<br>";
        }else if(type.equals("INFJ")){
            des=des+"INFJ<br>";
            des=des+"1.因为坚忍、创意及必须达成的意图而能成功。<br>";
            des=des+"2.会在工作中投注最大的努力。<br>";
            des=des+"3.默默强力的、诚挚的及用心的关切他人。<br>";
            des=des+"4.因坚守原则而受敬重。<br>";
            des=des+"5.提出造福大众利益的明确远景而为人所尊敬与追随。<br>";
            des=des+"6.追求创见、关系及物质财物的意义及关联。<br>";
            des=des+"7.想了解什么能激励别人及对他人具洞察力。<br>";
            des=des+"8.光明正大且坚信其价值观。<br>";
            des=des+"9.有组织且果断地履行其愿景。<br>";
        }else if(type.equals("INTJ")){
            des=des+"INTJ<br>";
            des=des+"1.具强大动力与本意来达成目的与创意—固执顽固者。<br>";
            des=des+"2.有宏大的愿景且能快速在众多外界事件中找出有意义的模范。<br>";
            des=des+"3.对所承负职务，具良好能力于策划工作并完成。<br>";
            des=des+"4.具怀疑心、挑剔性、独立性、果决，对专业水准及绩效要求高。<br>";
        }else if(type.equals("ISTP")){
            des=des+"ISTP<br>";
            des=des+"1.冷静旁观者—安静、预留余地、弹性及会以无偏见的好奇心与未预期原始的幽默观察与分析。<br>";
            des=des+"2.有兴趣于探索原因及效果，技术事件是为何及如何运作且使用逻辑的原理组构事实、重视效能。<br>";
            des=des+"3.擅长于掌握问题核心及找出解决方式。<br>";
            des=des+"4.分析成事的缘由且能实时由大量资料中找出实际问题的核心。<br>";
        }else if(type.equals("ISFP")){
            des=des+"ISFP<br>";
            des=des+"1.羞怯的、安宁和善地、敏感的、亲切的、且行事谦虚。<br>";
            des=des+"2.喜于避开争论，不对他人强加已见或价值观。<br>";
            des=des+"3.无意于领导却常是忠诚的追随者。<br>";
            des=des+"4.办事不急躁，安于现状无意于以过度的急切或努力破坏现况，且非成果导向。<br>";
            des=des+"5.喜欢有自有的空间及照自订的时程办事。<br>";
        }else if(type.equals("INFP")){
            des=des+"INFP<br>";
            des=des+"1安静观察者，具理想性与对其价值观及重要之人具忠诚心。<br>";
            des=des+"2.希望在生活形态与内在价值观相吻合。<br>";
            des=des+"3.具好奇心且很快能看出机会所在。常担负开发创意的触媒者。<br>";
            des=des+"4.除非价值观受侵犯，行事会具弹性、适应力高且承受力强。<br>";
            des=des+"5.具想了解及发展他人潜能的企图。想作太多且作事全神贯注。<br>";
            des=des+"6.对所处境遇及拥有不太在意。<br>";
            des=des+"7.具适应力、有弹性除非价值观受到威胁。<br>";
        }else if(type.equals("INTP")){
            des=des+"INTP<br>";
            des=des+"1.安静、自持、弹性及具适应力。<br>";
            des=des+"2.特别喜爱追求理论与科学事理。<br>";
            des=des+"3.习于以逻辑及分析来解决问题—问题解决者。<br>";
            des=des+"4.最有兴趣于创意事务及特定工作，对聚会与闲聊无大兴趣。<br>";
            des=des+"5.追求可发挥个人强烈兴趣的生涯。<br>";
            des=des+"6.追求发展对有兴趣事务之逻辑解释。<br>";
        }else if(type.equals("ESTP")){
            des=des+"ESTP<br>";
            des=des+"1.擅长现场实时解决问题—解决问题者。<br>";
            des=des+"2.喜欢办事并乐于其中及过程。<br>";
            des=des+"3.倾向于喜好技术事务及运动，交结同好友人。<br>";
            des=des+"4.具适应性、容忍度、务实性；投注心力于会很快具成效工作。<br>";
            des=des+"5.不喜欢冗长概念的解释及理论。<br>";
            des=des+"6.最专精于可操作、处理、分解或组合的真实事务。<br>";
        }else if(type.equals("ESFP")){
            des=des+"ESFP<br>";
            des=des+"1.外向、和善、接受性、乐于分享喜乐予他人。<br>";
            des=des+"2.喜欢与他人一起行动且促成事件发生，在学习时亦然。<br>";
            des=des+"3.知晓事件未来的发展并会热列参与。<br>";
            des=des+"4.最擅长于人际相处能力及具备完备常识，很有弹性能立即适应他人与环境。<br>";
            des=des+"5.对生命、人、物质享受的热爱者。<br>";
        }else if(type.equals("ENFP")){
            des=des+"ENFP<br>";
            des=des+"1.充满热忱、活力充沛、聪明的、富想象力的，视生命充满机会但期能得自他人肯定与支持。<br>";
            des=des+"2.几乎能达成所有有兴趣的事。<br>";
            des=des+"3.对难题很快就有对策并能对有困难的人施予援手。<br>";
            des=des+"4.依赖能改善的能力而无须预作规划准备。<br>";
            des=des+"5.为达目的常能找出强制自己为之的理由。<br>";
            des=des+"6.即兴执行者。<br>";
        }else if(type.equals("ENTP")){
            des=des+"ENTP<br>";
            des=des+"1.反应快、聪明、长于多样事务。<br>";
            des=des+"2.具激励伙伴、敏捷及直言讳专长。<br>";
            des=des+"3.会为了有趣对问题的两面加予争辩。<br>";
            des=des+"4.对解决新及挑战性的问题富有策略，但会轻忽或厌烦经常的任务与细节。<br>";
            des=des+"5.兴趣多元，易倾向于转移至新生的兴趣。<br>";
            des=des+"6.对所想要的会有技巧地找出逻辑的理由。<br>";
            des=des+"7.长于看清楚他人，有智能去解决新或有挑战的问题。<br>";
        }else if(type.equals("ESTJ")){
            des=des+"ESTJ<br>";
            des=des+"1.务实、真实、事实倾向，具企业或技术天份。<br>";
            des=des+"2.不喜欢抽象理论；最喜欢学习可立即运用事理。<br>";
            des=des+"3.喜好组织与管理活动且专注以最有效率方式行事以达致成效。<br>";
            des=des+"4.具决断力、关注细节且很快作出决策—优秀行政者。<br>";
            des=des+"5.会忽略他人感受。<br>";
            des=des+"6.喜作领导者或企业主管。<br>";
            des=des+"7.做事风格比较偏向于权威指挥性。<br>";
        }else if(type.equals("ESFJ")){
            des=des+"ESFJ<br>";
            des=des+"1.诚挚、爱说话、合作性高、受欢迎、光明正大的—天生的合作者及活跃的组织成员。<br>";
            des=des+"2.重和谐且长于创造和谐。<br>";
            des=des+"3.常作对他人有益事务。<br>";
            des=des+"4.给予鼓励及称许会有更佳工作成效。<br>";
            des=des+"5.最有兴趣于会直接及有形影响人们生活的事务。<br>";
            des=des+"6.喜欢与他人共事去精确且准时地完成工作。<br>";
        }else if(type.equals("ENFJ")){
            des=des+"ENFJ<br>";
            des=des+"1.热忱、易感应及负责任的、具有能鼓励他人的领导风格。<br>";
            des=des+"2.对别人所想或希求会表达真正关切且切实用心去处理。<br>";
            des=des+"3.能怡然且技巧性地带领团体讨论或演示文稿提案。<br>";
            des=des+"4.爱交际、受欢迎及富同情心。<br>";
            des=des+"5.对称许及批评很在意。<br>";
            des=des+"6.喜欢带引别人且能使别人或团体发挥潜能。<br>";
        }else if(type.equals("ENTJ")){
            des=des+"ENTJ<br>";
            des=des+"1.坦诚、具决策力的活动领导者。<br>";
            des=des+"2.长于发展与实施广泛的系统以解决组织的问题。<br>";
            des=des+"3.专精于具内涵与智能的谈话如对公众演讲。<br>";
            des=des+"4.乐于经常吸收新知且能广开信息管道。<br>";
            des=des+"5.易生过度自信，会强于表达自已创见。<br>";
            des=des+"6.喜于长程策划及目标设定。";
        }
        return des;
        
    }

    
    
    
   

    @Override
    public SysResult save(Answer t) {
        // TODO Auto-generated method stub
        return null;
    }
    

    
}
	
