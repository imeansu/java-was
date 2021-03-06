package framework.config;

import application.controller.MemoController;
import application.controller.UserController;
import application.db.DataSource;
import application.db.repository.MemoRepository;
import application.db.repository.UserRepository;
import application.domain.MemoService;
import application.domain.UserService;

public class Beans {

    // 의존성 역방향으로 생성 및 주입
    public static DataSource dataSource = new DataSource();
    public static UserRepository userRepository = new UserRepository(dataSource);
    public static MemoRepository memoRepository = new MemoRepository(dataSource);
    public static UserService userService = new UserService(userRepository);
    public static MemoService memoService = new MemoService(memoRepository);
    public static UserController userController = new UserController(userService);
    public static MemoController memoController = new MemoController(memoService);
}
