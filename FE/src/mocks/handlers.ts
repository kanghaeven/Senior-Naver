import { rest } from "msw";

interface PostLoginReqBody {
  email: string;
  password: string;
}

export const handlers = [
  rest.post<PostLoginReqBody>("/login", async (req, res, ctx) => {
    const { email, password } = req.body;
    const finded = uesrs.find(user => user.email === email);
    if (!finded) {
      return res(ctx.status(401));
    }

    return res(
      ctx.status(200),
      ctx.delay(2000),
      ctx.json({
        id: finded.id,
        email: finded.email,
        name: finded.name,
        profileImg: finded.profileImg,
      }),
    );
  }),
];
