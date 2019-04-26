package test.others;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptTest {
	public static void main(String args[]) throws ScriptException, NoSuchMethodException, IOException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");

		// 声明一个msg 变量
		engine.put("msg", "this is msg!");

		String jsCode = "var a = 10; var b =20;print(a+b);";
		// 执行javascript代码
		engine.eval(jsCode);
		// 修改变量
		engine.eval("msg = 'this is msg2!';print(msg);");
		// 使用java获取变量
		System.err.println(engine.get("msg"));
		// 执行js方法
		String jsFunc = "function add(a,b){return a+b}";
		engine.eval(jsFunc);
		Invocable invocable = (Invocable) engine;
		Object res = invocable.invokeFunction("add", new Object[] { 5, 7 });
		System.err.println(res);

		// 执行js文件
//		URL url = ScriptTest.class.getClassLoader().getResource("test.js");
//		FileReader reader = new FileReader(url.getPath());
		FileReader reader = new FileReader(new File("/work/test/1121/test.js"));
		engine.eval(reader);
		reader.close();

	}
}