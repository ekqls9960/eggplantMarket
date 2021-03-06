package teamB.market.web.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import teamB.market.domain.item.Item;
import teamB.market.domain.member.Member;
import teamB.market.domain.rate.Rate;
import teamB.market.domain.rate.mapper.RateMapper;
import teamB.market.domain.shipping.Shipping;
import teamB.market.domain.shipping.Status;
import teamB.market.domain.shipping.mapper.ShippingMapper;
import teamB.market.web.item.service.ItemService;
import teamB.market.web.member.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	private final ItemService itemService;
	private final ShippingMapper shippingMapper;
	private final MemberService memberService;
	private final RateMapper rateMapper;

	@RequestMapping("/myList/{memberId}")
	public String myOrderList(@PathVariable("memberId") Long memberId, Model model) {

		List<Shipping> ls = shippingMapper.findByMemberId(memberId);
		List<Item> readyItem = new ArrayList<Item>();
		List<Item> shippingItem = new ArrayList<Item>();
		List<Item> completeItem = new ArrayList<Item>();

		for (int i = 0; i < ls.size(); i++) {
			Item item = itemService.findById(ls.get(i).getItemId());
			if (item != null) {
				Status status = shippingMapper.findByItemId(item.getId()).getShippingStatus();
				if (status.equals(Status.READY)) {
					readyItem.add(item);
				} else if (status.equals(Status.SHIPPING)) {
					shippingItem.add(item);
				} else if (status.equals(Status.COMPLETE)) {
					completeItem.add(item);
				}
			}
		}

		model.addAttribute("readyItem", readyItem);
		model.addAttribute("shippingItem", shippingItem);
		model.addAttribute("completeItem", completeItem);

		Member loginMember = memberService.findById(memberId);
		model.addAttribute("loginMember", loginMember);

		return "order/myList";
	}

	// 판매자가 출고완료 클릭했을 때 동작
	@GetMapping("/release/{id}")
	public String releaseItem(@PathVariable("id") Long itemId) {
		// 출고 완료된 아이템 배송 상태 바꿔주기
		Shipping shipping = shippingMapper.findByItemId(itemId);
//		shipping.setShippingStatus(Status.SHIPPING);
		shippingMapper.updateShippingStatus(shipping.getId(), Status.SHIPPING);
		Long memberId = itemService.findById(itemId).getMemberId();
		return "redirect:/item/myList/" + memberId;
	}

	// 구매자가 구매확정 눌렀을 때 동작
	@GetMapping("/confirm/{id}")
	public String confirmOrder(@PathVariable("id") Long itemId, Model model) {
		// 구매 확정
		Shipping shipping = shippingMapper.findByItemId(itemId);
		// shipping.setShippingStatus(Status.COMPLETE);
		shippingMapper.updateShippingStatus(shipping.getId(), Status.COMPLETE);

		// 구매 확정되면 판매자 객체 포인트 업데이트
		Item item = itemService.findById(itemId);
		Member seller = memberService.findById(item.getMemberId());
		// seller.setPoint(item.getPrice());
		memberService.updatePoint(item.getPrice(), seller.getId());

		model.addAttribute("item", itemService.findById(itemId));

		// 구매 확정 후 별점 페이지로 이동
		return "order/rating";
	}

	@PostMapping("/rating/{id}")
	public String giveRating(@PathVariable("id") Long itemId, @RequestParam("buyerEmail") String buyerEmail,
			@RequestParam("rating") String rating) {
		// 구매자 정보
		long memberId = memberService.findByEmail(buyerEmail).getId();
		// rate 객체 저장
		Rate rate = new Rate();
		rate.setItemId(itemId);
		rate.setMemberId(memberId);
		rate.setRating(Float.valueOf(rating));
		System.out.println(rate);
		rateMapper.save(rate);

		// 판매자 정보
		long sellerId = itemService.findById(itemId).getMemberId();
		// 여기에서 평점 평균 값 넣어주기
		// 판매자 평점 업데이트
		Float ratingAvg = rateMapper.selectRatingAvg(sellerId);
		memberService.updateRatingAvg(ratingAvg, sellerId);

		return "redirect:/order/myList/" + memberId;
	}
}
