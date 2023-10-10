import { Module } from '@nestjs/common';
import { UsersService } from './users/users.service';
import { EmailService } from './email/email.service';
import { UsersController } from './users/users.controller';

@Module({
  imports: [],
  controllers: [UsersController],
  providers: [UsersService, EmailService],
})
export class AppModule {}
