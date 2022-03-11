/*
 * Initial Problem: Tried to configuration auth manager builder to check db against 
 * Google authorization of a user. 
 * 
 * Solution: Create custom handler for OidcUserService; 
 * Override loadUser() function 
 */

//@EnableWebSecurity
//@Configuration
//public class WebConfig extends WebSecurityConfigurerAdapter{
//	
//	@Autowired
//	private UserRepository userRepo;
//	
//
//	
//	public WebConfig(UserRepository userRepo) {
//		this.userRepo = userRepo;
//	}
//	
////	@Override
////	protected void configure(AuthenticationManagerBuilder authenticator) throws Exception {
////		System.out.println("Find by email");
////		authenticator.userDetailsService(userEmail -> userRepo
////				.loadByUsername(userEmail));
////	}
//
//
//}
