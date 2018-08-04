package RikkaJB.RikkaJavaBot;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class commandsforyou extends ListenerAdapter {

	
	public static JDA api;

	public void GuildController(Guild guild) {
	}

	@Override
	public void onReady(ReadyEvent event) {
		System.out.println("[UCPlugin] JDA is ready! Bot should be online.");
	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		event.getJDA();
		event.getResponseNumber();

		User author = event.getAuthor();
		Message message = event.getMessage();
		event.getChannel();

		String msg = message.getContentDisplay();

		author.isBot();

		if (event.isFromType(ChannelType.TEXT)) {
			Guild guild = event.getGuild();
			TextChannel textChannel = event.getTextChannel();
			Member member = event.getMember();

			String name;
			if (message.isWebhookMessage()) {
				name = author.getName();
			} else {
				name = member.getEffectiveName();
			}

			System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
		} else if (event.isFromType(ChannelType.PRIVATE)) {
			event.getPrivateChannel();

			System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
		} else if (event.isFromType(ChannelType.GROUP)) {
			Group group = event.getGroup();
			String groupName = group.getName();

			System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
		}
	}
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		int ROUNDED=0;
		int AVATAR_X=0;
	    int AVATAR_Y=0;
	    int widths=328;
	    int heights=135;
	    User user = event.getUser();
		 String avatar_url = user.getAvatarUrl();
            if(avatar_url==null)
            {
                avatar_url = user.getDefaultAvatarUrl();
            }
            
			try {
				BufferedImage welcome = ImageIO.read(new File("welcome2.png"));
				Graphics g = welcome.getGraphics();
				//int width = user.getName().length();
				int width =  788;
				int height = 340;
				g.setColor(Color.RED);
				//g.setFont(new Font("Dialog", Font.PLAIN, width*20));
				if(user.getName().length() <=14) {
                    g.setFont(new Font("Fabulous", Font.PLAIN, 35));
                    drawCenteredString(user.getName(), width, height*2-51, g);
                }
                else if(user.getName().length()>14 && user.getName().length()<=20) {
                    g.setFont(new Font("Fabulous", Font.PLAIN, 30));
                    drawCenteredString(user.getName(), width, height*2-51, g);
                }
                else if(user.getName().length()>20) {
                    g.setFont(new Font("Fabulous", Font.BOLD, 25));
                    drawCenteredString(user.getName(), width, height*2-51, g);
                }
				URL url = new URL(avatar_url);
                /* We have to spoof a web browser or we'll get hit with 403 access denied messages */
                HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
                httpcon.addRequestProperty("User-Agent","Mozilla/4.0");
                InputStream input_stream = httpcon.getInputStream();
                BufferedImage img = ImageIO.read(input_stream);

                /* Creating our scaled avatar */
                BufferedImage scaled_avatar = new BufferedImage(95,
                        95, img.TYPE_INT_ARGB);
                // scales the input image to the output image
                Graphics2D g2d = scaled_avatar.createGraphics();
                if(ROUNDED==1)
                    g2d.setClip(new Ellipse2D.Float(0,0,95,95));
                g2d.drawImage(img, 0, 0, 95, 95, null);
                g2d.dispose();
                input_stream.close();
                
                g.drawImage(scaled_avatar, width/2-49, height/2+3, null);
				g.dispose();
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(welcome, "png", baos);
                byte[] banner_out = baos.toByteArray();
                baos.close();
				
                
                event.getGuild().getDefaultChannel().sendFile(banner_out,"welcome2.png").queue();
				/*
				ImageIO.write(welcome, "png", new File("tests.png"));
				event.getChannel().sendFile(new File("tests.png")).queue();
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
public void drawCenteredString(String s, int w, int h, Graphics g) {
	FontMetrics fm = g.getFontMetrics();
	int x = (w - fm.stringWidth(s)) / 2;
	int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	g.drawString(s, x, y);
}
}
