package com.aiw.xiaochengxu.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiw.api.base.controller.BaseController;
import com.aiw.entity.KeyValue;
import com.aiw.entity.Page;
import com.aiw.entity.SysResult;
import com.aiw.xiaochengxu.entity.WXUser;
import com.aiw.xiaochengxu.mapper.WXUserMapper;
import com.aiw.zycp.util.HttpsClient;
import com.aiw.zycp.util.Mail;
import com.aiw.zycp.util.QuestionUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller(value="WXUserController")
@RequestMapping(value="/xiaochengxu/user")
public class WXUserController extends BaseController<WXUserMapper, WXUser>{
    
    @Autowired
    WXUserMapper wxUserMapper;
    
    @RequestMapping(value = "/login")
    @ResponseBody
    public SysResult wx() throws JsonParseException, JsonMappingException, IOException{
        String code = request.getParameter("code");
        String rs = HttpsClient.getSessionKey(code);
        ObjectMapper ob = new ObjectMapper();
        Map map = ob.readValue(rs.getBytes(), Map.class);
        
        String openid = (String)map.get("openid");
        WXUser wxUser = new WXUser();
        wxUser.setOpenid(openid);
        
        List<WXUser> list = wxUserMapper.select(wxUser);
        if(list == null || list.size() == 0) {
            wxUserMapper.insert(wxUser);
        }
        list = wxUserMapper.select(wxUser);
        return SysResult.oK(list.get(0));
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
    public SysResult update(WXUser t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult delete(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SysResult list(Page page, WXUser t) {
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

    public  static  List<List<KeyValue>>  getDISC()  {
        List<List<KeyValue>> list = null;
        FileReader fr = null;
        try {
            fr = new FileReader(QuestionUtil.class.getResource("/disc.txt").getFile());
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            int i = 0;
            list = new ArrayList<List<KeyValue>>();
            List<KeyValue> ti = null;
            KeyValue keyValue = null;
            while((line = br.readLine()) != null){
                line = line.trim();
                if(i % 5 == 0){
                    ti = new ArrayList<KeyValue>();
                    list.add(ti);
                }else{
                    keyValue = new KeyValue();
                    keyValue.setKey(line.substring(0, line.length()-1));
                    keyValue.setValue(line.substring(line.length()-1, line.length()));
                    ti.add(keyValue);
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    
    
    public  static  Map<String,String>  getDISCRS()  {
        Map<String,String> map = new HashMap<String, String>();
        String key = null;
        FileReader fr = null;
        try {
            fr = new FileReader(QuestionUtil.class.getResource("/disc_rs.txt").getFile());
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while((line = br.readLine()) != null){
                line = line.trim();
                if(line.contains("Dominance－支配型/控制者") || line.contains("Influence－活泼型/社交者") || line.contains("Steadiness－稳定型/支持者") || line.contains("Compliance－完美型/服从者")){
                    key = line;
                    map.put(key, "");
                }
                map.put(key, map.get(key)+"<br>"+line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public SysResult save(WXUser t) {
        // TODO Auto-generated method stub
        return null;
    }
    

    
}
	
