package com.itpass.quiz.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itpass.quiz.auth.SecuritySession;
import com.itpass.quiz.entity.Quiz;
import com.itpass.quiz.entity.QuizRecord;
import com.itpass.quiz.entity.UserAccount;
import com.itpass.quiz.form.QuizForm;
import com.itpass.quiz.service.QuizService;
import com.itpass.quiz.service.QuizRecordService;
import com.itpass.quiz.service.LoginService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizService service;

	@Autowired
	QuizRecordService r_service;

	@Autowired
	LoginService l_service;
	
	@Autowired
	SecuritySession securitySession;

	//「form-backing bean」の初期化
	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();
		return form;
	}

	// 一覧を表示
	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		//ログインしているユーザの名前を取得
		String userName = securitySession.getUsername();
		//管理者かどうか判別
		Integer admin_id = l_service.accountId(userName);
		if (admin_id == 1) {
			model.addAttribute("admin", ""); //管理用画面へ
		} else {
			//ユーザのランクを確認
			UserAccount levelList = l_service.findLevelList(userName);
			//テストを受けていない(levelが0)なら、最初のテストを開始
			if (levelList.getTec_level() == 0) {
				model.addAttribute("firstMsg", "ようこそ");
				model.addAttribute("userName", userName);
			} else{
				Integer tec = levelList.getTec_level();
				Integer man = levelList.getMan_level();
				Integer str = levelList.getStr_level();
				List<String> resultTec;
				List<String> resultMan;
				List<String> resultStr;
				model.addAttribute("u_name", userName);
				
				if(tec == 1) {
					resultTec = r_service.findTecList1(userName);
					model.addAttribute("tec", "C");
				}else if(tec == 2) {
					resultTec = r_service.findTecList2(userName);
					model.addAttribute("tec", "B");
				}else if(tec == 3) {
					resultTec = r_service.findTecList3(userName);
					model.addAttribute("tec", "A");
				}else {
					resultTec = r_service.findTecList4(userName);
					model.addAttribute("tec", "S");
				}
				
				if(man == 1) {
					resultMan = r_service.findManList1(userName);
					model.addAttribute("man", "C");
				}else if(man == 2) {
					resultMan = r_service.findManList2(userName);
					model.addAttribute("man", "B");
				}else if(man == 3) {
					resultMan = r_service.findManList3(userName);
					model.addAttribute("man", "A");
				}else {
					resultMan = r_service.findManList4(userName);
					model.addAttribute("man", "S");
				}
				
				if(str == 1) {
					resultStr = r_service.findStrList1(userName);
					model.addAttribute("str", "C");
				}else if(str == 2) {
					resultStr = r_service.findStrList2(userName);
					model.addAttribute("str", "B");
				}else if(str == 3) {
					resultStr = r_service.findStrList3(userName);
					model.addAttribute("str", "A");
				}else {
					resultStr = r_service.findStrList4(userName);
					model.addAttribute("str", "S");
				}
				
				if (resultTec.size() == 0) {
					model.addAttribute("tecProp", "解いた問題はありません。");
				} else {
					Long tecCount = resultTec.stream().filter( i -> i.equals("○")).count();
					Integer tecSize = resultTec.size();
					Double tecPercent = 100 * (double)tecCount / (double)tecSize;
					model.addAttribute("tecSize", tecSize);
					model.addAttribute("tecProp", String.format("%.1f", tecPercent) + "%");
				}
				if (resultMan.size() == 0) {
					model.addAttribute("manProp", "解いた問題はありません。");
				} else {
					Long manCount = resultMan.stream().filter( i -> i.equals("○")).count();
					Integer manSize = resultMan.size();
					Double manPercent = 100 * (double)manCount / (double)manSize;
					model.addAttribute("manSize", manSize);
					model.addAttribute("manProp", String.format("%.1f", manPercent) + "%");
				}
				if (resultStr.size() == 0) {
					model.addAttribute("strProp", "解いた問題はありません。");
				} else {
					Long strCount = resultStr.stream().filter( i -> i.equals("○")).count();
					Integer strSize = resultStr.size();
					Double strPercent = 100 * (double)strCount / (double)strSize;
					model.addAttribute("strSize", strSize);
					model.addAttribute("strProp", String.format("%.1f", strPercent) + "%");
				}
			}
		}
		// 問題の新規登録設定
		quizForm.setNewQuiz(true);
		//掲示板の一覧を取得する
		List<Quiz> list = service.getAllQuiz();
		// 表示用「Model」への格納
		model.addAttribute("list", list);
		model.addAttribute("title", "登録用フォーム");
		return "crud";
	}

	// データを1件挿入
	@PostMapping("/insert")
	public String insert(@Validated QuizForm quizForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		// FormからEntityへの詰め替え
		Quiz quiz = new Quiz();
		quiz.setTarget(quizForm.getTarget());
		quiz.setDivide(quizForm.getDivide());
		quiz.setLevel1(quizForm.getLevel1());
		quiz.setLevel2(quizForm.getLevel2());
		quiz.setStatement(quizForm.getStatement());
		quiz.setOrder1(quizForm.getOrder1());
		quiz.setOrder2(quizForm.getOrder2());
		quiz.setOrder3(quizForm.getOrder3());
		quiz.setOrder4(quizForm.getOrder4());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setHint(quizForm.getHint());
		// 入力チェック
		if (!bindingResult.hasErrors()) {
			service.insertQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "登録が完了しました");
			return "redirect:/quiz";
		} else {
			// エラー時は一覧を再表示
			return showList(quizForm, model);
		}
	}

	// データを1件取得し、フォーム内に表示
	@GetMapping("/{id}")
	public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
		// Entityを取得(Optionalでラップ)
		Optional<Quiz> quizOpt = service.selectOneById(id);
		// Formへの詰め直し
		Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
		// Formがnullでなければ中身を取り出す
		if (quizFormOpt.isPresent()) {
			quizForm = quizFormOpt.get();
		}
		// 更新用のModelを作成
		makeUpdateModel(quizForm, model);
		return "crud";
	}

	// 更新用のModelを作成
	private void makeUpdateModel(QuizForm quizForm, Model model) {
		model.addAttribute("id", quizForm.getId());
		quizForm.setNewQuiz(false);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("title", "更新用フォーム");
	}

	// idをKeyにしてデータを更新する
	@PostMapping("/update")
	public String update(
			@Validated QuizForm quizForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		// FormからEntityに詰め直す
		Quiz quiz = makeQuiz(quizForm);
		// 入力チェック
		if (!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト(個々の編集ページ)
			service.updateQuiz(quiz);
			redirectAttributes.addFlashAttribute("complete", "更新が完了しました");
			// 更新画面を表示
			return "redirect:/quiz/" + quiz.getId();
		} else {
			// 更新用のModelを作成
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}

	// ---------- 【以下はFormとDomainObjectの詰めなおし】 ----------
	// FormからEntityに詰め直して戻り値として返す
	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setTarget(quizForm.getTarget());
		quiz.setDivide(quizForm.getDivide());
		quiz.setLevel1(quizForm.getLevel1());
		quiz.setLevel2(quizForm.getLevel2());
		quiz.setStatement(quizForm.getStatement());
		quiz.setOrder1(quizForm.getOrder1());
		quiz.setOrder2(quizForm.getOrder2());
		quiz.setOrder3(quizForm.getOrder3());
		quiz.setOrder4(quizForm.getOrder4());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setHint(quizForm.getHint());
		return quiz;
	}

	// EntityからFormに詰め直して戻り値として返す
	private QuizForm makeQuizForm(Quiz quiz) {
		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setTarget(quiz.getTarget());
		form.setDivide(quiz.getDivide());
		form.setLevel1(quiz.getLevel1());
		form.setLevel2(quiz.getLevel2());
		form.setStatement(quiz.getStatement());
		form.setOrder1(quiz.getOrder1());
		form.setOrder2(quiz.getOrder2());
		form.setOrder3(quiz.getOrder3());
		form.setOrder4(quiz.getOrder4());
		form.setAnswer(quiz.getAnswer());
		form.setHint(quiz.getHint());
		return form;
	}

	// idをKeyにしてデータを削除
	@PostMapping("/delete")
	public String delete(
			@RequestParam("id") String id,
			Model model,
			RedirectAttributes redirectAttributes) {
		//タスクを1件削除してリダイレクト
		service.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました");
		return "redirect:/quiz";
	}

	// ランダムに問題を一つ取得
	private Optional<Quiz> makeQuestion(){
		Random rand = new Random();
		Integer value = rand.nextInt(3);
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		Optional<Quiz> question;
		if (value == 0) {
			if (tec == 1) {
				question = service.selectTec1();
			} else if (tec == 2) {
				question = service.selectTec2();
			} else if (tec == 3) {
				Random randnum = new Random();
				Integer randvalue = randnum.nextInt(10);
				if((randvalue <= 6)) {
					question = service.selectTec3();
				}else {
					question = service.selectTec2();
				}
			} else {
				question = service.selectTec4();
			}
		} else if (value == 1) {
			if (man == 1) {
				question = service.selectMan1();
			} else if (man == 2) {
				question = service.selectMan2();
			} else if (man == 3) {
				Random randnum = new Random();
				Integer randvalue = randnum.nextInt(10);
				if(randvalue <= 6) {
					question = service.selectMan3();
				}else {
					question = service.selectMan2();
				}	
			} else {
				question = service.selectMan4();
			}
		} else {
			if (str == 1) {
				question = service.selectStr1();
			} else if (str == 2) {
				question = service.selectStr2();
			} else if (str == 3) {
				Random randnum = new Random();
				Integer randvalue = randnum.nextInt(10);
				if(randvalue <= 6) {
					question = service.selectStr3();
				}else {
					question = service.selectStr2();
				}
			} else {
				question = service.selectStr4();
			}
		}
		return question;
	}

	//履歴に追加する用のレベル
	private Integer makeLevel(Integer targetNum) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		Integer level;
		if (targetNum == 1) {
			level = tec;
		} else if (targetNum == 2) {
			level = man;
		} else {
			level = str;
		}
		return level;
	}
	
	// 問題の資格試験コード
	private String targetNum(Integer num) {
		String msg;
		if (num == 1) {
			msg = "テクノロジー";
		} else if (num == 2) {
			msg = "マネジメント";
		} else {
			msg = "ストラテジー";
		}
		return msg;
	}
	
	// 問題の分類コード
	private String divideNum(Integer num) {
		String msg;
		if (num == 1) {
			msg = "基礎理論";
		} else if (num == 2) {
			msg = "アルゴリズムとプログラミング";
		} else if (num == 3) {
			msg = "コンピューター構成要素";
		} else if (num == 4) {
			msg = "システム構成要素";
		} else if (num == 5) {
			msg = "ソフトウェア";
		} else if (num == 6) {
			msg = "ハードウェア";
		} else if (num == 7) {
			msg = "情報デザイン";
		} else if (num == 8) {
			msg = "情報メディア";
		} else if (num == 9) {
			msg = "データベース";
		} else if (num == 10) {
			msg = "ネットワーク";
		} else if (num == 11) {
			msg = "セキュリティ";
		} else if (num == 12) {
			msg = "システム開発技術";
		} else if (num == 13) {
			msg = "ソフトウェア開発管理技術";
		} else if (num == 14) {
			msg = "プロジェクトマネジメント";
		} else if (num == 15) {
			msg = "サービスマネジメント";
		} else if (num == 16) {
			msg = "システム監査";
		} else if (num == 17) {
			msg = "企業活動";
		} else if (num == 18) {
			msg = "法務";
		} else if (num == 19) {
			msg = "経営戦略マネジメント";
		} else if (num == 20) {
			msg = "技術戦略マネジメント";
		} else if (num == 21) {
			msg = "ビジネスインダストリ";
		} else if (num == 22) {
			msg = "システム戦略";
		} else {
			msg = "システム監査";
		}
		return msg;
	}
	
	// ランダムに問題を1件取得する時に被らないにするための変数
	private Integer q_num = 0;
	private Integer s_num;
	// 解答を始めたときの時刻
	private LocalDateTime s_time;
	// 取得した時刻のレイアウト
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd/ HH:mm:ss");

	// ---------- 【以下はクイズで遊ぶ機能】 ----------
	// ランダムで1件取得し、画面に表示
	@GetMapping("/play")
	public String showQuiz(QuizForm quizForm, Model model) {
		//Quizを取得(Optionalでラップ)
		Optional<Quiz> quizOpt = makeQuestion();
		s_time = LocalDateTime.now();
		// 値が入っているか判定する
		if(quizOpt.isPresent()) {// QuizFormへの詰め直し
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
			s_num = quizForm.getId();
			if(q_num == s_num) {
				return showQuiz(quizForm, model);
			} else {
				String targetnum = targetNum(quizForm.getTarget());
				String dividenum = divideNum(quizForm.getDivide());
				model.addAttribute("targetNum", targetnum);
				model.addAttribute("divideNum", dividenum);
				q_num = s_num;
			}
		} else {
			model.addAttribute("msg", "問題がありません・・・");
			return "play";
		}
		// 表示用「Model」への格納
		model.addAttribute("quizForm", quizForm);
		return "play";
	}
	
	// 解答したときに系列のランクが上がるかの判定
	private void judgeLevel(Model model) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		List<String> resultTec;
		List<String> resultMan;
		List<String> resultStr;
		if(tec == 1) {
			resultTec = r_service.findTecList1(userName);
			if (resultTec.size() == 20) {
				Long tecCount = resultTec.stream().filter( i -> i.equals("○")).count();
				if ((tecCount / 20) >= 0.6) {
					l_service.updateTecLevel(tec + 1, userName);
					model.addAttribute("tecLevel", "テクノロジー系列のランクが上がりました。");
				}
			}
		}else if(tec == 2) {
			resultTec = r_service.findTecList2(userName);
			if (resultTec.size() == 15) {
				Long tecCount = resultTec.stream().filter( i -> i.equals("○")).count();
				if ((tecCount / 15) >= 0.7) {
					l_service.updateTecLevel(tec + 1, userName);
					model.addAttribute("tecLevel", "テクノロジー系列のランクが上がりました。");
				}
			}
		}else if(tec == 3) {
			resultTec = r_service.findTecList3(userName);
			if (resultTec.size() == 10) {
				Long tecCount = resultTec.stream().filter( i -> i.equals("○")).count();
				if ((tecCount / 10) >= 0.8) {
					l_service.updateTecLevel(tec + 1, userName);
					model.addAttribute("tecLevel", "テクノロジー系列のランクが上がりました。");
				}
			}
		}else {
			model.addAttribute("tecLevel", "");
		}
		
		if(man == 1) {
			resultMan = r_service.findManList1(userName);
			if (resultMan.size() == 20) {
				Long manCount = resultMan.stream().filter( i -> i.equals("○")).count();
				if ((manCount / 20) >= 0.6) {
					l_service.updateManLevel(man + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else if(man == 2) {
			resultMan = r_service.findManList2(userName);
			if (resultMan.size() == 15) {
				Long manCount = resultMan.stream().filter( i -> i.equals("○")).count();
				if ((manCount / 15) >= 0.7) {
					l_service.updateManLevel(man + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else if(man == 3) {
			resultMan = r_service.findManList3(userName);
			if (resultMan.size() == 10) {
				Long manCount = resultMan.stream().filter( i -> i.equals("○")).count();
				if ((manCount / 10) >= 0.8) {
					l_service.updateManLevel(man + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else {
			model.addAttribute("tecLevel", "");
		}
		
		if(str == 1) {
			resultStr = r_service.findStrList1(userName);
			if (resultStr.size() == 20) {
				Long strCount = resultStr.stream().filter( i -> i.equals("○")).count();
				if ((strCount / 20) >= 0.6) {
					l_service.updateStrLevel(str + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else if(str == 2) {
			resultStr = r_service.findStrList2(userName);
			if (resultStr.size() == 15) {
				Long strCount = resultStr.stream().filter( i -> i.equals("○")).count();
				if ((strCount / 15) >= 0.7) {
					l_service.updateStrLevel(str + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else if(str == 3) {
			resultStr = r_service.findStrList3(userName);
			if (resultStr.size() == 10) {
				Long strCount = resultStr.stream().filter( i -> i.equals("○")).count();
				if ((strCount / 10) >= 0.8) {
					l_service.updateStrLevel(str + 1, userName);
					model.addAttribute("tecLevel", "ストラテジー系列のランクが上がりました。");
				}
			}
		}else {
			model.addAttribute("tecLevel", "");
		}
	}
	

	// クイズの正解/不正解を判定
	@PostMapping("/check")
	public String checkQuiz(QuizForm quizForm, @RequestParam Integer answer, Model model) {
		Integer targetNum = quizForm.getTarget();
		Integer divideNum = quizForm.getDivide();
		Integer level = makeLevel(targetNum);
		// 正誤の結果を表示すると共に結果を記録
		if (service.checkQuiz(quizForm.getId(), answer)) {
			r_service.addQuizRecord(inQuizRecord(quizForm, answer, "○", targetNum, divideNum, level));
			model.addAttribute("msg","正解です");
		} else {
			r_service.addQuizRecord(inQuizRecord(quizForm, answer, "×", targetNum, divideNum, level));
			model.addAttribute("msg","不正解です");
		}
		judgeLevel(model);
		return "answer";
	}

	// 問題を記録する時の要素
	private QuizRecord inQuizRecord(QuizForm quizForm, Integer answer, String quiz_result, Integer target, Integer divide, Integer level) {
		QuizRecord q_record = new QuizRecord();
		q_record.setUser_name(securitySession.getUsername());
		q_record.setQ_id(quizForm.getId());
		q_record.setQuiz_answer(service.checkQuizAnswer(quizForm.getId()));
		q_record.setUser_answer(answer);
		q_record.setStart_time(dtf.format(s_time).toString());
		q_record.setFin_time(dtf.format(LocalDateTime.now()).toString());
		q_record.setQ_result(quiz_result);
		q_record.setTarget(target);
		q_record.setDivide(divide);
		q_record.setLevel(level);
		return q_record;
	}
	
	// 「ランダム選出」or「選択したカテゴリーから選出」を選ぶ画面
	@GetMapping("/select")
	public String showSelect(Model model) {
		model.addAttribute(new QuizForm());
		return "select";
	}
	
	// テクノロジー系列から1件選出
	@GetMapping("/selectTec")
	public String showSelectTec(QuizForm quizForm, Model model) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		Optional<Quiz> question;
		s_time = LocalDateTime.now();
		if (tec == 1) {
			question = service.selectTec1();
		} else if (tec == 2) {
			question = service.selectTec2();
		} else if (tec == 3) {
			Random randnum = new Random();
			Integer randvalue = randnum.nextInt(10);
			if((randvalue <= 6)) {
				question = service.selectTec3();
			}else {
				question = service.selectTec2();
			}
		} else {
			question = service.selectTec4();
		}
		Optional<Quiz> quizOpt = question;
		if(quizOpt.isPresent()) {
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
			s_num = quizForm.getId();
			if(q_num == s_num) {
				return showSelectTec(quizForm, model);
			}else {
				String targetnum = targetNum(quizForm.getTarget());
				String dividenum = divideNum(quizForm.getDivide());
				model.addAttribute("targetNum", targetnum);
				model.addAttribute("divideNum", dividenum);
				q_num = s_num;
			}
		}else {
			model.addAttribute("msg", "問題の実装をお待ちください");
			return "selectPlay";
		}
		model.addAttribute("quizForm", quizForm);
		return "selectPlay";
	}
	
	// マネジメント系列から1件選出
	@GetMapping("/selectMan")
	public String showSelectMan(QuizForm quizForm, Model model) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		Optional<Quiz> question;
		s_time = LocalDateTime.now();
		if (man == 1) {
			question = service.selectMan1();
		} else if (man == 2) {
			question = service.selectMan2();
		} else if (man == 3) {
			Random randnum = new Random();
			Integer randvalue = randnum.nextInt(10);
			if((randvalue <= 6)) {
				question = service.selectMan3();
			}else {
				question = service.selectMan2();
			}
		} else {
			question = service.selectMan4();
		}
		Optional<Quiz> quizOpt = question;
		if(quizOpt.isPresent()) {
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
			s_num = quizForm.getId();
			if(q_num == s_num) {
				return showSelectMan(quizForm, model);
			}else {
				String targetnum = targetNum(quizForm.getTarget());
				String dividenum = divideNum(quizForm.getDivide());
				model.addAttribute("targetNum", targetnum);
				model.addAttribute("divideNum", dividenum);
				q_num = s_num;
			}
		}else {
			model.addAttribute("msg", "問題の実装をお待ちください");
			return "selectPlay";
		}
		model.addAttribute("quizForm", quizForm);
		return "selectPlay";
	}
	
	// ストラテジー系列から1件選出
	@GetMapping("/selectStr")
	public String showSelectStr(QuizForm quizForm, Model model) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		Optional<Quiz> question;
		s_time = LocalDateTime.now();
		if (str == 1) {
			question = service.selectStr1();
		} else if (str == 2) {
			question = service.selectStr2();
		} else if (str == 3) {
			Random randnum = new Random();
			Integer randvalue = randnum.nextInt(10);
			if((randvalue <= 6)) {
				question = service.selectStr3();
			}else {
				question = service.selectStr2();
			}
		} else {
			question = service.selectStr4();
		}
		Optional<Quiz> quizOpt = question;
		if(quizOpt.isPresent()) {
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
			s_num = quizForm.getId();
			if(q_num == s_num) {
				return showSelectStr(quizForm, model);
			}else {
				String targetnum = targetNum(quizForm.getTarget());
				String dividenum = divideNum(quizForm.getDivide());
				model.addAttribute("targetNum", targetnum);
				model.addAttribute("divideNum", dividenum);
				q_num = s_num;
			}
		}else {
			model.addAttribute("msg", "問題の実装をお待ちください");
			return "selectPlay";
		}
		model.addAttribute("quizForm", quizForm);
		return "selectPlay";
	}
	
	@PostMapping("/checkSelectPlay")
	public String checkSelectPlay(QuizForm quizForm, @RequestParam Integer answer, Model model) {
		Integer targetNum = quizForm.getTarget();
		Integer divideNum = quizForm.getDivide();
		Integer level = makeLevel(targetNum);
		if (service.checkQuiz(quizForm.getId(), answer)) {
			r_service.addQuizRecord(inQuizRecord(quizForm, answer, "○", targetNum, divideNum, level));
			model.addAttribute("msg","正解です");
		} else {
			r_service.addQuizRecord(inQuizRecord(quizForm, answer, "×", targetNum, divideNum, level));
			model.addAttribute("msg","不正解です");
		}
		judgeLevel(model);
		if(targetNum == 1) {
			model.addAttribute("tecmsg", "テクノロジー");
		}else if(targetNum == 2) {
			model.addAttribute("manmsg", "マネジメント");
		}else {
			model.addAttribute("strmsg", "ストラテジー");
		}
		return "checkSelectPlay";
	}

	/** 好きな問題を選べるモードの変更により削除
	private Integer numa;
	private Integer numb;
	
	@GetMapping("/chooseLevel")
	public String showChoose(Model model) {
		model.addAttribute(new QuizForm());
		return "chooseLevel";
	}
	
	@PostMapping("/choose")
	public String settingChoose(QuizForm quizForm, Model model, RedirectAttributes redirectAttributes) {
		numa = quizForm.getTar();
		numb = quizForm.getVide();
		redirectAttributes.addFlashAttribute("setting","設定が完了しました。");
		return "redirect:/quiz/chooseLevel";
	}
	
	@GetMapping("/choosequiz")
	public String showPlayQuiz(QuizForm quizForm, Model model) {
		//Quizを取得(Optionalでラップ)
		Optional<Quiz> quizOpt = service.chooseQuiz(numa, numb);
		// 値が入っているか判定する
		if(quizOpt.isPresent()) {// QuizFormへの詰め直し
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
		} else {
			model.addAttribute("msg", "問題がありません・・・");
			return "choosePlay";
		}
		// 表示用「Model」への格納
		model.addAttribute("quizForm", quizForm);
		return "choosePlay";
	}
	
	@PostMapping("/choosecheck")
	public String checkPlayQuiz(QuizForm quizForm, @RequestParam Integer answer, Model model){
		QuizRecord q_record = new QuizRecord();
		if (service.checkQuiz(quizForm.getId(), answer)) {
			q_record.setUser_name(securitySession.getUsername());
			q_record.setQ_id(quizForm.getId());
			q_record.setQ_result("〇");
			q_record.setRecord_time(LocalDateTime.now().toString());
			r_service.addQuizRecord(q_record);
			model.addAttribute("msg","正解です");
		} else {
			q_record.setUser_name(securitySession.getUsername());
			q_record.setQ_id(quizForm.getId());
			q_record.setQ_result("✕");
			q_record.setRecord_time(LocalDateTime.now().toString());
			r_service.addQuizRecord(q_record);
			model.addAttribute("msg","不正解です");
		}
		return "chooseAnswer";
	}
	**/
	
	// ユーザ情報
	@GetMapping("/userInfo")
	public String showUserInfo(Model model) {
		String userName = securitySession.getUsername(); // ユーザの名前を取得
		UserAccount levelList = l_service.findLevelList(userName); // ユーザの各系列のランクを取得
		Integer tec = levelList.getTec_level(); // テクノロジーのランク
		Integer man = levelList.getMan_level(); // マネジメントのランク
		Integer str = levelList.getStr_level(); // ストラテジーのランク
		List<String> resultTec; // テクノロジー系列の履歴
		List<String> resultMan; // マネジメント系列の履歴
		List<String> resultStr; // ストラテジー系列の履歴

		// ユーザの名前を表示
		model.addAttribute("u_name", userName);
		
		// 各系列のランクを表示
		if(tec == 1) {
			resultTec = r_service.findTecList1(userName);
			model.addAttribute("tec", "C");
		}else if(tec == 2) {
			resultTec = r_service.findTecList2(userName);
			model.addAttribute("tec", "B");
		}else if(tec == 3) {
			resultTec = r_service.findTecList3(userName);
			model.addAttribute("tec", "A");
		}else {
			resultTec = r_service.findTecList4(userName);
			model.addAttribute("tec", "S");
		}
		
		if(man == 1) {
			resultMan = r_service.findManList1(userName);
			model.addAttribute("man", "C");
		}else if(man == 2) {
			resultMan = r_service.findManList2(userName);
			model.addAttribute("man", "B");
		}else if(man == 3) {
			resultMan = r_service.findManList3(userName);
			model.addAttribute("man", "A");
		}else {
			resultMan = r_service.findManList4(userName);
			model.addAttribute("man", "S");
		}
		
		if(str == 1) {
			resultStr = r_service.findStrList1(userName);
			model.addAttribute("str", "C");
		}else if(str == 2) {
			resultStr = r_service.findStrList2(userName);
			model.addAttribute("str", "B");
		}else if(str == 3) {
			resultStr = r_service.findStrList3(userName);
			model.addAttribute("str", "A");
		}else {
			resultStr = r_service.findStrList4(userName);
			model.addAttribute("str", "S");
		}
		
		// 各系列の正答率を計算、表示
		if (resultTec.size() == 0) {
			model.addAttribute("tecProp", "解いた問題はありません。");
		} else {
			Long tecCount = resultTec.stream().filter( i -> i.equals("○")).count();
			Integer tecSize = resultTec.size();
			Double tecPercent = 100 * (double)tecCount / (double)tecSize;
			model.addAttribute("tecSize", tecSize);
			model.addAttribute("tecProp", String.format("%.1f", tecPercent) + "%");
		}
		if (resultMan.size() == 0) {
			model.addAttribute("manProp", "解いた問題はありません。");
		} else {
			Long manCount = resultMan.stream().filter( i -> i.equals("○")).count();
			Integer manSize = resultMan.size();
			Double manPercent = 100 * (double)manCount / (double)manSize;
			model.addAttribute("manSize", manSize);
			model.addAttribute("manProp", String.format("%.1f", manPercent) + "%");
		}
		if (resultStr.size() == 0) {
			model.addAttribute("strProp", "解いた問題はありません。");
		} else {
			Long strCount = resultStr.stream().filter( i -> i.equals("○")).count();
			Integer strSize = resultStr.size();
			Double strPercent = 100 * (double)strCount / (double)strSize;
			model.addAttribute("strSize", strSize);
			model.addAttribute("strProp", String.format("%.1f", strPercent) + "%");
		}
		return "userInfo";
	}

	// 最初のテスト用の変数
	private List<Integer> firstList = new ArrayList<Integer>();
	private Integer firstNum = 0;
	private Integer divideOne = 0;
	private Integer divideTwo = 0;
	private Integer divideThree = 0;
	
	// 初めてこのWebアプリに来た人がやるテスト
	// テストの問題を選出
	@GetMapping("/firstTest")
	public String showFirstTest(Model model) {
		List<Integer> list1 = service.selectDivideOne();
		List<Integer> list2 = service.selectDivideTwo();
		List<Integer> list3 = service.selectDivideThree();
		firstList.addAll(list1);
		firstList.addAll(list2);
		firstList.addAll(list3);
		model.addAttribute(new QuizForm());
		return "firstTest";
	}
	
	// 問題を表示
	@GetMapping("/playFirstTest")
	public String playFirstTest(QuizForm quizForm, Model model) {
		Optional<Quiz> firstOpt = service.selectOneById(firstList.get(firstNum));
		firstNum ++;
		Optional<QuizForm> firstTestOpt = firstOpt.map(t -> makeQuizForm(t));
		quizForm = firstTestOpt.get();
		String targetnum = targetNum(quizForm.getTarget());
		String dividenum = divideNum(quizForm.getDivide());
		model.addAttribute("targetNum", targetnum);
		model.addAttribute("divideNum", dividenum);
		model.addAttribute("testNum", firstNum + " 問目");
		model.addAttribute("quizForm", quizForm);
		return "playFirstTest";
	}
	
	// 解答の正誤を判定し、表示
	@PostMapping("/checkFirstTest")
	public String checkFirstTest(QuizForm quizForm, @RequestParam Integer answer, Model model) {
		if ((firstNum == 1)||(firstNum == 2)||(firstNum == 3)||(firstNum == 4)||(firstNum == 5)) {
			if (service.checkQuiz(quizForm.getId(), answer)) {
				model.addAttribute("msg","正解です");
			} else {
				model.addAttribute("msg","不正解です");
				divideOne ++;
			}
		} else if ((firstNum == 6)||(firstNum == 7)||(firstNum == 8)||(firstNum == 9)||(firstNum == 10)) {
			if (service.checkQuiz(quizForm.getId(), answer)) {
				model.addAttribute("msg","正解です");
			} else {
				model.addAttribute("msg","不正解です");
				divideTwo ++;
			}
		} else if ((firstNum == 11)||(firstNum == 12)||(firstNum == 13)||(firstNum == 14)) {
			if (service.checkQuiz(quizForm.getId(), answer)) {
				model.addAttribute("msg","正解です");
			} else {
				model.addAttribute("msg","不正解です");
				divideThree ++;
			}
		} else {
			if (service.checkQuiz(quizForm.getId(), answer)) {
				model.addAttribute("msg","正解です");
			} else {
				model.addAttribute("msg","不正解です");
				divideThree ++;
			}
			model.addAttribute("finMsg", "終了");
		}
		return "checkFirstTest";
	}
	
	// テストの結果からユーザの各系列のランクを決定
	@PostMapping("/resultFirstTest")
	public String resultFirstTest(Model model) {
		String userName = securitySession.getUsername();
		if (divideOne == 0) {
			l_service.updateTecLevel(2, userName);
		} else {
			l_service.updateTecLevel(1, userName);
		}
		
		if (divideTwo == 0) {
			l_service.updateManLevel(2, userName);
		} else {
			l_service.updateManLevel(1, userName);
		}
		
		if (divideThree == 0) {
			l_service.updateStrLevel(2, userName);
		} else {
			l_service.updateStrLevel(1, userName);
		}
		
		model.addAttribute("endMsg", "テスト終了");
		return "resultFirstTest";
	}
}