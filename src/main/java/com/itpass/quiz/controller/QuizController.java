package com.itpass.quiz.controller;

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

import com.itpass.quiz.entity.Quiz;
import com.itpass.quiz.form.QuizForm;
import com.itpass.quiz.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	QuizService service;

	//「form-backing bean」の初期化
	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();
		return form;
	}

	// 一覧を表示
	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		// 新規登録設定
		quizForm.setNewQuiz(true);
		//掲示板の一覧を取得する
		Iterable<Quiz> list = service.selectAll();
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

	// ---------- 【以下はクイズで遊ぶ機能】 ----------
	// ランダムで1件取得し、画面に表示
	@GetMapping("/play")
	public String showQuiz(QuizForm quizForm, Model model) {
		//Quizを取得(Optionalでラップ)
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		// 値が入っているか判定する
		if(quizOpt.isPresent()) {// QuizFormへの詰め直し
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();
		} else {
			model.addAttribute("msg", "問題がありません・・・");
			return "play";
		}
		// 表示用「Model」への格納
		model.addAttribute("quizForm", quizForm);
		return "play";
	}
	// クイズの正解/不正解を判定
	@PostMapping("/check")
	public String checkQuiz(QuizForm quizForm, @RequestParam Integer answer, Model model){
		if (service.checkQuiz(quizForm.getId(), answer)) {
			model.addAttribute("msg","正解です");
		} else {
			model.addAttribute("msg","不正解です");
		}
		return "answer";
	}
}