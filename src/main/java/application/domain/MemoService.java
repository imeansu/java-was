package application.domain;

import application.db.repository.MemoRepository;

import java.util.List;

public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }


    public List<Memo> getAllMemos() {
        return memoRepository.findAll();
    }

    public void save(Memo memo) {
        memoRepository.addMemo(memo);
    }

}
