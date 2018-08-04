package RikkaJB.RikkaJavaBot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.util.logging.Logger;

public class App extends ListenerAdapter {
	
	public static JDA api;
	private JDA jda;
	public static void main(String[] args) {
		try {
			if(Ref.isBOT) {
				JDA api = new JDABuilder(AccountType.BOT).setToken("your bot's token goes here").addEventListener(new NextLaunches()).buildBlocking();
				api.getPresence().setGame(Game.of(GameType.DEFAULT, "Made for Pengwen"));
				api.getPresence().setStatus(OnlineStatus.ONLINE);
				api.addEventListener(new Commands());
			} else {
				JDA api = new JDABuilder(AccountType.CLIENT).setToken("your bot's token goes here").addEventListener(new NextLaunches()).buildBlocking();
				api.getPresence().setGame(Game.of(GameType.DEFAULT, "Made for Pengwen"));
				api.getPresence().setStatus(OnlineStatus.ONLINE);
				api.addEventListener(new Commands());
			}
		} catch (LoginException | IllegalArgumentException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
