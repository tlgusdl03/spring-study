package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    //실무에서는 동시성 이슈를 예방하기 위해 AtomicHashMap 사용
    private static Map<Long, Member> store = new HashMap<>();
    //마찬가지로 AtomicLong 사용
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        //id 세팅
        member.setId(++sequence);
        //map에 저장
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //람다를 사용하여 루프를 돌리면서 이름이 같은지 확인함
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
